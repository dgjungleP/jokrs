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

<div class=" grid grid-cols-6">
	<div class=" shadow-lg  fixed min-h-[40vh]  mt-[40px] ml-[20px] p-5 min-w-[14vw]">
		<h1 class=" font-bold text-3xl text-center mb-2">Posts</h1>
		{#each blogList as blog (blog)}
			<a href={`/blog/posts/${blog.id}`}>{blog.title}</a>
		{/each}
	</div>
	<div class=" col-start-2 col-end-6 w-full h-full block">
		<slot />
	</div>
</div>
