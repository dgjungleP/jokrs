<script>
	import { getTodoList, applyTodo, getCountDownList } from '$lib/home.js';
	import { onMount } from 'svelte';
	import Modal from '../compoment/Modal.svelte';
	import Echarts from '../compoment/Echarts.svelte';
	/** @type {import('./$types').PageData} */
	export let data;
	let todoList = [];
	let countDownList = [];

	let currentTodo = {};
	let showApplyModal = false;
	const bgColor = data.bgColor;
	const sunburstData = data.data;
	const colors = data.colors;
	const fresh = () => {
		getTodoList()
			.then((response) => {
				todoList = response.data;
			})
			.catch((e) => {
				console.log(e.message);
			});
		getCountDownList()
			.then((response) => {
				countDownList = response.data;
			})
			.catch((e) => {
				console.log(e.message);
			});
	};
	const handleApply = (item) => {
		currentTodo = item;
		showApplyModal = true;
	};
	const handleModalApply = () => {
		if (!currentTodo.actual) {
			alert('Sorry you should input actual proccess');
			return;
		}
		showApplyModal = false;
		applyTodo(currentTodo).then((_r) => {
			fresh();
		});
	};
	console.log(sunburstData);
	onMount(() => {
		fresh();
	});

	$: option = {
		series: {
			type: 'sunburst',
			emphasis: {
				focus: 'descendant'
			},
			data: sunburstData,
			radius: [0, '90%'],
			label: {
				rotate: 'radial'
			}
		}
	};
</script>

<div class=" grid grid-cols-6 h-full  ">
	<div class=" left col-span-4 flex justify-center items-center ">
		<!-- charts -->
		<Echarts {option} width={'99%'} height={'99%'} />
		<!-- charts end -->
	</div>
	<div class=" mid col-span-1    ">
		<div class=" flex flex-col gap-3 py-5  h-full  bg-[#d9d1b4] px-2   ">
			<!-- TODO LIST -->
			<div class=" list todo-list   ">
				<h2 class=" text-center">Daliy Todo List</h2>
				{#each todoList as item}
					<li>
						<div class=" todo">
							<span class:finished={item.finished}
								>{item.objectiveName + ':' + item.keyResultName}</span
							>
							<button class=" sm-btn" on:click={() => handleApply(item)} disabled={item.finished}
								>Apply</button
							>
						</div>
					</li>
				{/each}
			</div>
		</div>
	</div>
	<div class=" right col-span-1">
		<div class=" flex flex-col gap-3 py-5  h-full    bg-[#6ac59c]  bg-slate-200 px-2  ">
			<!-- COUNT DOWN  -->
			<div class=" list count-down-list  ">
				<h2 class=" text-center">Count Down List</h2>
				{#each countDownList as item}
					<li>
						<div class=" count-down">
							<span>{item.keyResultName}</span>
							<span>{item.leftTime}{'Day' + (item.leftTime > 1 ? 's' : '')}</span>
						</div>
					</li>
				{/each}
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
{#if showApplyModal}
	<Modal on:close={() => (showApplyModal = false)}>
		<svelte:fragment slot="header">
			<span class:finished={currentTodo.finished}
				>{currentTodo.objectiveName + ':' + currentTodo.keyResultName}</span
			>
		</svelte:fragment>
		<svelte:fragment slot="content">
			<label class=" block">
				<span>Expect:</span>
				<input bind:value={currentTodo.expect} disabled />
			</label>
			<label class=" block">
				<span>Current:</span>
				<input bind:value={currentTodo.current} disabled />
			</label>
			<label class=" block">
				<span>Actual:</span>
				<input bind:value={currentTodo.actual} disabled={currentTodo.finished} />
			</label>
		</svelte:fragment>
		<svelte:fragment slot="foot">
			<button class=" btn" on:click={handleModalApply} disabled={currentTodo.finished}>
				Apply</button
			>
		</svelte:fragment>
	</Modal>
{/if}

<!-- Modal end -->
<style>
	.list {
		@apply bg-opacity-95 bg-white;
		border-radius: 5px;
		display: inline-block;
		box-shadow: 1px 1px 2px 0px;
		min-width: 15%;
	}

	.list li {
		list-style: none;
		margin-top: 5px;
		padding: 0 5px;
	}
	.list li:last-child {
		padding-bottom: 10px;
	}
	.list .finished {
		text-decoration: line-through;
	}
	.todo,
	.count-down {
		display: flex;
		justify-content: space-between;
	}
</style>
