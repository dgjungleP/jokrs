package com.jungle.okrs.vo;

import cn.hutool.core.util.ObjectUtil;
import com.jungle.okrs.entity.File;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Data
public class FileTree {
    private Node<File> root;

    public FileTree() {
        root = Node.of(new File());
    }

    public void add(File currentFile) {
        Node<File> parent = root.findParent(data -> ObjectUtil.equals(data.getId(), currentFile.getParentId()));
        Optional.ofNullable(parent).ifPresent(data -> data.subNode.add(Node.of(currentFile)));
    }

    public boolean container(Long parentId) {
        return root.container(data -> ObjectUtil.equals(data.getId(), parentId));
    }

    @Data
    public static class Node<E> {
        private E currentNode;
        private List<Node<E>> subNode = new ArrayList<>();

        public static <E> Node<E> of(E currentFile) {
            Node<E> node = new Node<>();
            node.currentNode = currentFile;
            return node;
        }

        public E getEntity() {
            return currentNode;
        }

        public boolean container(Function<E, Boolean> check) {
            return check.apply(currentNode) || subNode.stream().anyMatch(data -> data.container(check));
        }

        public Node<E> findParent(Function<E, Boolean> check) {
            if (check.apply(currentNode)) {
                return this;
            }
            return subNode.stream().map(data -> data.findParent(check)).filter(ObjectUtil::isNotNull).findAny()
                    .orElse(null);
        }
    }
}
