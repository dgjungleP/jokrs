<script>
	import File from '../../compoment/File.svelte';
	import Folder from '../../compoment/Folder.svelte';

	/** @type {import('./$types').PageData} */
	export let data;
	let treeData = [
		{
			name: 'hello',
			files: [{ name: 'hello1.1' }, { name: 'hello1.2' }, { name: 'hello1.3' }],
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
</script>

{#each treeData as item}
	{#if item.isFile}
		<File
			file={item}
			on:select={(event) => {
				console.log(event.detail);
			}}
		/>
	{:else}
		<Folder
			folder={item}
			on:select={(event) => {
				console.log(event.detail);
			}}
		/>
	{/if}
{/each}
