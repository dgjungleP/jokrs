<script>
	import { createEventDispatcher } from 'svelte';
	const dispatch = createEventDispatcher();
	const close = () => {
		dispatch('close');
	};
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<div class="modal-bakend" on:click={close} />
<divc class="modal ">
	<slot name="header" />
	<hr />
	<slot name="content" />
	<hr />
	<!-- svelte-ignore a11y-autofocus -->
	<div class=" flex">
		<button autofocus on:click={close} class="block btn">Close</button>
		<slot name="foot" />
	</div>
</divc>
