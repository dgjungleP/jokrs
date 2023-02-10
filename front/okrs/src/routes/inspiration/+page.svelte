<script>
	import { onMount } from 'svelte';
	import Markdown from '../../compoment/Markdown.svelte';
	import File from '../../compoment/File.svelte';
	import Folder from '../../compoment/Folder.svelte';
	import Modal from '../../compoment/Modal.svelte';
	import { getFileTree, tryEditFile, getFileById, getFolder } from '$lib/file.js';
	/** @type {import('./$types').PageData} */
	export let data;
	$: inspirationList = [];
	$: currentInspiration = { content: '' };
	$: folderList = [];
	$: preCurrent = null;
	$: newFile = {};
	$: showAddModal = false;
	onMount(() => {
		getFileTree().then((response) => {
			inspirationList = response.data;
		});
		getFolder().then((response) => {
			folderList = response.data;
		});
	});
	const handleFileSave = (item) => {
		if (item.type) {
			item.isFolder = item.type === 'folder';
		}
		tryEditFile(item);
		showAddModal = false;
	};
	const handleSelect = (event) => {
		currentInspiration = { content: '' };
		const detail = event.detail;
		if (preCurrent) {
			preCurrent.classList.remove('selected');
		}
		detail.current.classList.add('selected');
		if (!detail.item.isFolder) {
			getFileById(detail.item.id).then((response) => {
				currentInspiration = response.data;
				console.log(currentInspiration);
			});
		}
		preCurrent = detail.current;
	};
</script>

<div class=" inspiration-content sm-border">
	<div class=" left-content">
		<div class=" btn-group flex  gap-1">
			<button
				class=" sm-btn"
				on:click={(e) => {
					newFile.type = 'file';
					showAddModal = true;
				}}>Create File</button
			>
			<button
				class=" sm-btn"
				on:click={(e) => {
					newFile.type = 'folder';
					showAddModal = true;
				}}>Create Folder</button
			>
		</div>
		<div>
			{#each inspirationList as item, index (item)}
				{#if item.isFile}
					<File file={item.file} on:select={handleSelect} />
				{:else}
					<Folder folder={item} on:select={handleSelect} />
				{/if}{/each}
		</div>
	</div>
	<div class=" right-content">
		<div class=" radom-content">Every day one content</div>
		<div class=" show-content row-span-4    ">
			<div class=" h-full  relative  ">
				<div class=" absolute bottom-0 top-0 left-0 right-0">
					<Markdown bind:value={currentInspiration.content} mod={'editor'} />
					<button
						class=" sm-btn absolute right-[190px] bottom-0"
						on:click={() => handleFileSave(currentInspiration)}>Submit</button
					>
				</div>
			</div>
			<div />
		</div>
	</div>
</div>

<!-- Modal -->

{#if showAddModal}
	<Modal on:close={() => (showAddModal = false)}>
		<div slot="header">Objetives</div>
		<div slot="content">
			<label class=" block">
				<span>Type:</span>
				<input bind:value={newFile.type} disabled />
			</label>
			<label class=" block">
				<span>Title:</span>
				<input bind:value={newFile.title} />
			</label>
			<label class=" block">
				<span>FilType:</span>
				<select bind:value={newFile.fileType}>
					<option value="INSPIRATION">INSPIRATION</option>
					<option value="BLOG">BLOG</option>
				</select>
			</label>
			<label class=" block">
				<span>Parent:</span>
				<select bind:value={newFile.parentId}>
					<option value={null}>None</option>
					{#each folderList as item}
						<option value={item.id}>{item.title}</option>
					{/each}
				</select>
			</label>
		</div>
		<div slot="foot">
			<button class="btn" on:click={() => handleFileSave(newFile)}>Save</button>
		</div>
	</Modal>
{/if}

<!-- Modal end -->
<style>
	.inspiration-content {
		@apply grid grid-cols-5 w-full h-full;
	}
	.left-content {
		@apply col-span-1 border-r-4 flex flex-col;
	}
	.right-content {
		@apply col-span-4 grid grid-rows-5;
	}
	.radom-content {
		@apply row-span-1 border-b-4;
	}
</style>
