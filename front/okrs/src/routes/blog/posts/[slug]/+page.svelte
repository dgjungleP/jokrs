<script>
	import Markdown from '../../../../compoment/Markdown.svelte';
	/** @type {import('./$types').PageData} */
	export let data;
	console.log(data);
</script>

<div class=" header">
	<h1>Posts:<span class=" text-4xl font-bold">{data.post.title}</span></h1>
	<div class=" status">Status line</div>
</div>

<div class=" border  p-4 shadow">
	<Markdown value={data.post.content} />
</div>

<style>
	.header {
		@apply mb-5;
	}
</style>
