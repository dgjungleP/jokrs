<script>
	/** @type {import('./$types').LayoutData} */
	export let data;
</script>

<slot />

<h1>Posts</h1>

{#each data.posts as post (post.slug)}
	<h2>
		<a href={`/blog/posts/${post.slug}`}>{post.title}</a>
	</h2>
{/each}
