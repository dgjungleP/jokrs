<script>
	import { onMount } from 'svelte';
	import Markdown from '../../compoment/Markdown.svelte';
	import File from '../../compoment/File.svelte';
	import Folder from '../../compoment/Folder.svelte';

	/** @type {import('./$types').PageData} */
	export let data;
	$: inspirationList = [
		{
			name: 'hello',

			files: [
				{ name: 'hello1.1', content: 'Hello1' },
				{ name: 'hello1.2', content: 'Hello2' },
				{ name: 'hello1.3', content: 'Hello3' }
			],
			type: 'folder'
		},
		{
			name: 'hello',
			files: [
				{
					name: 'hello1.1',
					files: [{ name: 'hello1.1.1' }, { name: 'hello1.1.2' }, { name: 'hello1.1.3' }],
					type: 'folder'
				},
				{ name: 'hello1.2' },
				{
					name: 'hello2.3',
					files: [{ name: 'hello2.3.1' }, { name: 'hello2.3.2' }, { name: 'hello2.3.3' }],
					type: 'folder'
				}
			],
			type: 'folder'
		},
		{
			name: 'hello',
			files: [{ name: 'hello1.1' }, { name: 'hello1.2' }, { name: 'hello1.3' }],
			type: 'folder'
		}
	];
	$: currentInspiration = {};
	$: preCurrent = null;
	onMount(() => {});
	const handleSelect = (event) => {
		const detail = event.detail;
		if (preCurrent) {
			preCurrent.classList.remove('selected');
		}
		detail.current.classList.add('selected');
		if (detail.item.type !== 'folder') {
			currentInspiration = detail.item;
		}
		preCurrent = detail.current;
	};
</script>

<div class=" inspiration-content sm-border">
	<div class=" left-content">
		<div class=" btn-group flex  gap-1">
			<button class=" sm-btn">Create File</button>
			<button class=" sm-btn">Create Folder</button>
		</div>
		<div>
			{#each inspirationList as item, index (item)}
				{#if item.isFile}
					<File file={item} on:select={handleSelect} />
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
					<Markdown content={currentInspiration.content || ''} mod={'editor'} />
					<button class=" sm-btn absolute right-[190px] bottom-0">Submit</button>
				</div>
			</div>
			<div />
		</div>
	</div>
</div>

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
