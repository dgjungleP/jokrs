<script>
	import '../app.css';
	const menu = [
		{ name: 'Home', path: '/' },
		{ name: 'Okrs', path: '/okrs' },
		{ name: 'Blog', path: '/blog' },
		{ name: 'Skill Map', path: '/skill' },
		{ name: 'Inspiration', path: '/inspiration' },
		{ name: 'Test', path: '/test' }
	];
</script>

<div class=" header">
	<h1 class="  flex justify-center text-5xl font-black ">Jungle Kits</h1>
	<nav>
		{#each menu as item, index (item)}
			<a href={item.path}> {item.name}</a>
			{#if index != menu.length - 1}
				/
			{/if}
		{/each}
	</nav>

	<div class=" border-solid border-b-4 mt-10  " />
</div>

<slot />

<style>
	.header {
		@apply relative;
	}
</style>
