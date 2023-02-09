<script>
	import Table from '../../compoment/Table.svelte';
	import TimeLine from '../../compoment/TimeLine.svelte';
	import Card from '../../compoment/Card.svelte';
	import Modal from '../../compoment/Modal.svelte';
	import moment from 'moment';
	import { BASE_TIME_FORMATE } from '$lib/util.js';
	import {
		getKeyResultList,
		updateObjective,
		deleteObjective,
		getObjectives,
		updateKeyResult,
		getKeyResultByid,
		getActionList
	} from '$lib/jokrs.js';
	import { onMount } from 'svelte';
	/** @type {import('./$types').PageData} */
	export let data;
	let items = [];
	let timeLineList = [];
	let currentObjective = {};
	let currentKeyReultList = [];
	let tableTile = [
		{ name: 'Objective', key: 'objective' },
		{ name: 'Plan Time', key: 'planTime', width: 'w-[7vw]' },
		{ name: 'Progress', key: 'progress', width: 'w-[10vw]' }
	];
	let updateObjectiveBody = {};
	let updateKeyResultBody = {};

	let showAddObjective = false;
	let showEditObjective = false;

	let showAddKeyResult = false;
	let showEditKeyResult = false;
	const flushDate = () => {
		getObjectives().then((reponse) => {
			items = reponse.data;
		});
		if (currentObjective.id) {
			getKeyResult(currentObjective.id);
		}
		getActionList().then((reponse) => {
			timeLineList = reponse.data;
		});
	};
	flushDate();
	const handleShow = (event) => {
		const eventDetail = event.detail;
		currentObjective = eventDetail.item;
		getKeyResult(currentObjective.id);
	};
	const handleDeleteObjective = (event) => {
		deleteObjective(event.detail.item.id).then((response) => {
			if (event.detail.item.id == currentObjective.id) {
				currentObjective = {};
			}
			flushDate();
		});
	};

	const handleObjectiveChange = () => {
		updateObjectiveBody.planTime = moment(updateObjectiveBody.planTime);
		updateObjective(updateObjectiveBody).then((response) => {
			showAddObjective = showEditObjective = false;

			flushDate();
		});
	};
	const handleKeyResultChange = () => {
		updateKeyResultBody.planTime = moment(updateKeyResultBody.planTime);
		updateKeyResultBody.objectiveId = currentObjective.id;
		updateKeyResult(updateKeyResultBody).then((response) => {
			showAddKeyResult = showEditKeyResult = false;
			flushDate();
		});
	};
	const handleAddObjective = () => {
		updateObjectiveBody = {};
		showAddObjective = true;
	};
	const handleEditObjective = (event) => {
		console.log(event.detail.item);
		updateObjectiveBody = { ...event.detail.item };
		updateObjectiveBody.planTime = moment(updateObjectiveBody.planTime).format(BASE_TIME_FORMATE);
		showEditObjective = true;
	};
	const handleAddKeyResult = () => {
		updateKeyResultBody = {};
		showAddKeyResult = true;
	};
	const handleEditKeyResult = (event) => {
		getKeyResultByid(event.detail.item.id).then((response) => {
			updateKeyResultBody = response.data;
			updateKeyResultBody.planTime = moment(updateKeyResultBody.planTime).format(BASE_TIME_FORMATE);
			showEditKeyResult = true;
		});
	};

	const getKeyResult = (objectiveId) => {
		getKeyResultList(objectiveId).then((response) => {
			currentKeyReultList = response.data;
		});
	};
	onMount(() => flushDate());
</script>

<div class=" grid grid-cols-8">
	<div class="  col-span-7">
		<button class="btn" on:click={handleAddObjective}>Add Objective</button>
		<div class="okrs-table flex justify-center mt-5">
			<Table
				tableTitle={tableTile}
				tableData={items}
				on:showDetail={handleShow}
				on:edit={handleEditObjective}
				on:delete={handleDeleteObjective}
			/>
		</div>
		<hr class=" mt-4 mb-4" />
		{#if currentObjective.id}
			<button class="btn" on:click={handleAddKeyResult}>Add KeyResult</button>
			<div class=" mt-4">
				{#each currentKeyReultList as item}
					<Card {item} on:edit={handleEditKeyResult}>
						<div slot="title">{item.name}</div>
						<div slot="content">
							<div class=" block">
								<span>Description:{item.description}</span>
							</div>
							<div class=" block">
								<span>Plan Time:{moment(item.planTime).format(BASE_TIME_FORMATE)}</span>
							</div>
							<div class=" block">
								<span>Measure:{item.measure}</span>
							</div>
							<div class=" block">
								<span>Progress:{item.progress}</span>
							</div>
							<div class=" block">
								<span>Rate:{new Number((item.progress / item.measure) * 100).toFixed(2)}%</span>
							</div>
						</div>
					</Card>
				{/each}
			</div>
		{/if}
	</div>

	<div class=" float-right mt-[30%] ">
		<TimeLine items={timeLineList} />
	</div>
</div>

<!-- modal -->
{#if showAddObjective || showEditObjective}
	<Modal on:close={() => (showAddObjective = showEditObjective = false)}>
		<div slot="header">Objetives</div>
		<div slot="content">
			<label class=" block">
				<span>Name:</span>
				<input bind:value={updateObjectiveBody.name} />
			</label>
			<label class=" block">
				<span>Description:</span>
				<input bind:value={updateObjectiveBody.description} />
			</label>
			<label class=" block">
				<span>Plan Time:</span>
				<input type="date" bind:value={updateObjectiveBody.planTime} />
			</label>
		</div>
		<div slot="foot"><button class="btn" on:click={handleObjectiveChange}>Save</button></div>
	</Modal>
{/if}
{#if showAddKeyResult || showEditKeyResult}
	<Modal on:close={() => (showAddKeyResult = showEditKeyResult = false)}>
		<div slot="header">Objectivw:{currentObjective.name}`s KeyResult</div>
		<div slot="content">
			<label class=" block">
				<span>Name:</span>
				<input bind:value={updateKeyResultBody.name} />
			</label>
			<label class=" block">
				<span>Description:</span>
				<input bind:value={updateKeyResultBody.description} />
			</label>
			<label class=" block">
				<span>Plan Time:</span>
				<input type="date" bind:value={updateKeyResultBody.planTime} />
			</label>
			<label class=" block">
				<span>Measure:</span>
				<input type="number" bind:value={updateKeyResultBody.measure} />
			</label>
			{#if showEditKeyResult}
				<label class=" block">
					<span>Progress:</span>
					<input type="number" bind:value={updateKeyResultBody.progress} />
				</label>
			{/if}
			<label class=" block">
				<span>Weight:</span>
				<input type="number" bind:value={updateKeyResultBody.weight} />%
			</label>
		</div>
		<div slot="foot"><button class="btn" on:click={handleKeyResultChange}>Save</button></div>
	</Modal>
{/if}

<style>
</style>
