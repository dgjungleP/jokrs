<script>
	import moment from 'moment';
	export let items = [];
</script>

<ul class=" time-line">
	{#each items as item}
		<li class="time-line-item">
			<div class="time-line-point" />
			<div class="time-line-line" />
			<div class="time-line-content">
				<span>
					{moment(item.time).format('YYYY-MM-DD HH:MM')}
				</span>
				<br />
				<span class=" text-xs">
					{item.actionDescription}
				</span>
			</div>
		</li>
	{/each}
</ul>
