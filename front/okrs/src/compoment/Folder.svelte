<script>
	import File from './File.svelte';
	import { slide } from 'svelte/transition';
	import { createEventDispatcher } from 'svelte';
	const dispatch = createEventDispatcher();
	const select = (item) => {
		dispatch('select', { item: item, current: current });
	};
	export let folder;
	$: expanded = false;
	$: current = {};
	$: tags = folder.tags || [];
	const toggle = () => {
		expanded = !expanded;
	};
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<div
	class=" flex"
	class:expanded
	on:click={() => {
		select(folder);
		toggle();
	}}
	bind:this={current}
>
	{#if expanded}
		<svg
			xmlns="http://www.w3.org/2000/svg"
			fill="none"
			viewBox="0 0 24 24"
			stroke-width="1.5"
			stroke="currentColor"
			class="w-6 h-6"
		>
			<path
				stroke-linecap="round"
				stroke-linejoin="round"
				d="M3.75 9.776c.112-.017.227-.026.344-.026h15.812c.117 0 .232.009.344.026m-16.5 0a2.25 2.25 0 00-1.883 2.542l.857 6a2.25 2.25 0 002.227 1.932H19.05a2.25 2.25 0 002.227-1.932l.857-6a2.25 2.25 0 00-1.883-2.542m-16.5 0V6A2.25 2.25 0 016 3.75h3.879a1.5 1.5 0 011.06.44l2.122 2.12a1.5 1.5 0 001.06.44H18A2.25 2.25 0 0120.25 9v.776"
			/>
		</svg>
	{:else}
		<svg
			xmlns="http://www.w3.org/2000/svg"
			fill="none"
			viewBox="0 0 24 24"
			stroke-width="1.5"
			stroke="currentColor"
			class="w-6 h-6 folder-icon icon "
		>
			<path
				stroke-linecap="round"
				stroke-linejoin="round"
				d="M2.25 12.75V12A2.25 2.25 0 014.5 9.75h15A2.25 2.25 0 0121.75 12v.75m-8.69-6.44l-2.12-2.12a1.5 1.5 0 00-1.061-.44H4.5A2.25 2.25 0 002.25 6v12a2.25 2.25 0 002.25 2.25h15A2.25 2.25 0 0021.75 18V9a2.25 2.25 0 00-2.25-2.25h-5.379a1.5 1.5 0 01-1.06-.44z"
			/>
		</svg>
	{/if}

	<spna>{folder.name}</spna>
	<div class="tags">
		{#each tags as tag}
			<div class=" tag">{tag}</div>
		{/each}
	</div>
</div>

{#if expanded}
	<ul transition:slide={{ duration: 300 }}>
		{#each folder.files as file}
			<li>
				{#if file.type == 'folder'}
					<svelte:self folder={file} on:select />
				{:else}
					<File {file} on:select />
				{/if}
			</li>
		{/each}
	</ul>
{/if}

<style>
	ul {
		padding: 0.2em 0 0 0.5em;
		margin: 0 0 0 0.5em;
		list-style: none;
		border-left: 1px solid #eee;
	}
	li {
		padding: 0.2em 0;
	}
</style>
