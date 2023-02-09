<script>
	import { createEventDispatcher } from 'svelte';
	const dispatch = createEventDispatcher();
	export let item;
	const handleClickObjectiveEdit = (item) => {
		dispatch('edit', {
			item: item
		});
	};
</script>

<div class="card" on:dblclick={() => handleClickObjectiveEdit(item)}>
	<div class="card-title">
		<slot name="title" />
	</div>
	<hr />
	<div class="card-content">
		<slot name="content" />
	</div>
</div>
