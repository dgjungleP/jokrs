<script>
	import Markdown from '../../../../compoment/Markdown.svelte';
	/** @type {import('./$types').PageData} */
	export let data;
</script>

<h1>Posts:{data.post.title}</h1>

<div class=" border inline-block p-4">
	<Markdown content={data.post.content} />
</div>
