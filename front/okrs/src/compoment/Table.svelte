<script>
	import { createEventDispatcher } from 'svelte';
	import { BASE_TIME_FORMATE } from '$lib/util.js';
	import moment from 'moment';
	const dispatch = createEventDispatcher();
	export let tableData;
	export let tableTitle;
	const handleClickObjective = (item) => {
		dispatch('showDetail', {
			item: item
		});
	};
	const handleClickObjectiveDelete = (item) => {
		dispatch('delete', {
			item: item
		});
	};
	const handleClickObjectiveEdit = (item) => {
		dispatch('edit', {
			item: item
		});
	};
</script>

<div class=" flex justify-center w-full">
	<table class="border-collapse border border-slate-800 w-4/5">
		<thead class="bg-slate-200">
			{#if tableTitle && tableTitle.length > 0}
				<tr>
					{#each tableTitle as title}
						<td class="border border-slate-300 {title.width}">{title.name}</td>
					{/each}
					<td class="border border-slate-300  w-[8vw]">Action</td>
				</tr>
			{/if}
		</thead>
		{#if tableData && tableData.length > 0}
			<tbody>
				{#each tableData as item}
					<tr on:dblclick={() => handleClickObjective(item)}>
						<td class="border border-slate-300 ">{item.name}</td>
						<td class="border border-slate-300 text-center"
							>{moment(item.planTime).format(BASE_TIME_FORMATE)}</td
						>
						<td class="border border-slate-300 text-right"
							>{new Number(item.progress).toFixed(2)}%</td
						>
						<td class="border border-slate-300 "
							><button class="btn" on:click={() => handleClickObjectiveDelete(item)}>Delete</button
							><button class="btn" on:click={() => handleClickObjectiveEdit(item)}>Edit</button></td
						>
					</tr>
				{/each}
			</tbody>
		{/if}
	</table>
</div>
