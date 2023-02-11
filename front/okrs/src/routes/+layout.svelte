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

<div class=" flex flex-col min-h-full">
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
	<div class=" container min-w-full">
		<slot />
	</div>

	<footer class=" bg-slate-100 min-w-full ">
		<div class=" flex justify-center items-center">
			<div class="">
				<a href="https://github.com/dgjungleP" target="_blank" rel="noreferrer">
					<svg
						width="30px"
						height="30px"
						viewBox="0 0 64 64"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						stroke="#000000"
					>
						<path
							d="M20 56a4 4 0 0 0 4-4 8 8 0 0 1 2.35-5.67s-13.69-1.53-16.6-7.41A21.67 21.67 0 0 1 8 29.42 17.23 17.23 0 0 1 11.9 18a15.43 15.43 0 0 1-.77-4.82C11.13 11 11 10 12 8c4.48 0 8 .85 11.37 3.52A39.58 39.58 0 0 1 32 10.7a39.58 39.58 0 0 1 8.63.82C44 8.85 47.52 8 52 8c1 1.95.87 3 .87 5.18A15.43 15.43 0 0 1 52.1 18 17.23 17.23 0 0 1 56 29.42a21.67 21.67 0 0 1-1.75 9.5c-2.91 5.88-16.6 7.41-16.6 7.41A8 8 0 0 1 40 52a4 4 0 0 0 4 4"
						/>
						<path d="M24 50.88c-4 2-6.31-.43-8.75-2S11 46.75 8 48" />
						<ellipse cx="22.81" cy="31.72" rx="3.19" ry="4.82" /><ellipse
							cx="41.19"
							cy="31.72"
							rx="3.19"
							ry="4.82"
						/>
					</svg>
				</a>
			</div>
		</div>
	</footer>
</div>

<style>
	.header {
		@apply relative;
	}
	.container {
		flex: 1;
	}
</style>
