<script>
	import { Editor, Viewer } from 'bytemd';
	import gfm from '@bytemd/plugin-gfm';
	import mermaid from '@bytemd/plugin-mermaid';
	import 'bytemd/dist/index.css';
	import 'github-markdown-css';
	import './markdown.css';
	export let content;
	export let mod;
	$: value = content;
	$: plugins = [gfm(), mermaid()];
</script>

{#if mod === 'editor'}
	<Editor
		{value}
		{plugins}
		on:change={(e) => {
			value = e.detail.value;
		}}
	/>
{:else}
	<Viewer {value} {plugins} />
{/if}
