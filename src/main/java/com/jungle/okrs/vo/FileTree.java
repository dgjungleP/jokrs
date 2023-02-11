package com.jungle.okrs.vo;

import cn.hutool.core.util.ObjectUtil;
import com.jungle.okrs.entity.File;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class FileTree {
    private Node<File> root;

    public FileTree() {
        root = Node.of(new File());
    }

    public void add(File currentFile) {
        Node<File> parent = root.findParent(data -> ObjectUtil.equals(data.getId(), currentFile.getParentId()));
        Optional.ofNullable(parent).ifPresent(data -> data.files.add(Node.of(currentFile)));
    }

    public boolean container(Long parentId) {
        return root.container(data -> ObjectUtil.equals(data.getId(), parentId));
    }

    public List<File> getAllFolder() {
        return root.getAll(data -> Boolean.TRUE.equals(data.getIsFolder()));

    }

    @Data
    public static class Node<E> {
        private E file;
        private List<Node<E>> files = new ArrayList<>();

        public static <E> Node<E> of(E currentFile) {
            Node<E> node = new Node<>();
            node.file = currentFile;
            return node;
        }

        public E getEntity() {
            return file;
        }

        public boolean container(Function<E, Boolean> check) {
            return check.apply(file) || files.stream().anyMatch(data -> data.container(check));
        }

        public Node<E> findParent(Function<E, Boolean> check) {
            if (check.apply(file)) {
                return this;
            }
            return files.stream().map(data -> data.findParent(check)).filter(ObjectUtil::isNotNull).findAny()
                    .orElse(null);
        }


        public List<E> getAll(Predicate<E> check) {
            List<E> result = new ArrayList<>();
            if (file != null && check.test(file)) {
                result.add(file);
            }
            result.addAll(this.files.stream().map(data -> data.getAll(check)).flatMap(Collection::stream)
                    .collect(Collectors.toList()));
            return result;
        }
    }
}
