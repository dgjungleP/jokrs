<script>
	import { getBlogList } from '$lib/file.js';
	import { onMount } from 'svelte';
	/** @type {import('./$types').LayoutData} */
	export let data;
	$: blogList = [];
	onMount(() => {
		getBlogList().then((response) => {
			blogList = response.data;
		});
	});
</script>

<slot />

<h1>Posts</h1>

{#each blogList as blog (blog)}
	<h2>
		<a href={`/blog/posts/${blog.id}`}>{blog.title}</a>
	</h2>
{/each}
