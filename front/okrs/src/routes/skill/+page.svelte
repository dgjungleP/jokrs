<script>
	import { onMount } from 'svelte';
	import Echarts from '../../compoment/Echarts.svelte';
	import Modal from '../../compoment/Modal.svelte';
	import { getSkillList, addSkill } from '$lib/skill.js';
	/** @type {import('./$types').PageData} */
	export let data;

	$: typeList = categories.map((item) => item.name);
	$: categories = [];
	$: graphData = [];
	$: edges = [];
	$: option = {
		tooltip: {},
		legend: [
			{
				data: typeList
			}
		],
		series: [
			{
				type: 'graph',
				layout: 'force',
				animation: true,
				symbolSize: 50,
				roam: true,
				label: {
					show: true
				},
				edgeLabel: {
					fontSize: 20
				},
				data: graphData,
				force: {
					repulsion: 250,
					edgeLength: 200
				},
				edges: edges,
				categories: categories
			}
		]
	};
	let skillList = [];

	let currentSkill = {};
	let showSkillModal = false;
	const handleSkillChange = () => {
		addSkill(currentSkill).then((response) => {
			const data = response.data;
			console.log(data);
			edges.push(
				...data.relationList.map((item) => {
					return { source: item.sourceId - 1, target: item.targetId - 1 };
				})
			);
			flushCategory(data);
			data.skillList.forEach((item) => {
				tryAddSkills(item);
			});
			closeSKillModal();
		});
	};
	const closeSKillModal = () => {
		currentSkill = {};
		showSkillModal = false;
	};

	const tryAddSkills = (item) => {
		console.log(graphData.indexOf());
		if (currentSkill.id == item.id) {
			console.log(graphData.find((data) => data.id === item.id));
			const oldSkill = graphData.find((data) => data.id === item.id);
			oldSkill.name = item.name;
			oldSkill.value = item;
			oldSkill.symbolSize = item.name.length * 10 + 10;
			oldSkill.category = typeList.indexOf(item.type.name);
		} else {
			graphData.push({
				id: item.id,
				name: item.name,
				value: item,
				symbolSize: item.name.length * 10 + 10,
				category: typeList.indexOf(item.type.name)
			});
		}

		flushChart();
	};
	const flushCategory = (data) => {
		categories.push(...data.skillTypeList.filter((data) => typeList.indexOf(data.name) < 0));
		categories = [...new Set(categories)];
		typeList = categories.map((item) => item.name);
	};
	const flushChart = () => {
		option = {
			series: [
				{
					roam: true,
					data: graphData,
					edges: edges
				}
			]
		};
	};
	const handleSkillPointClick = (item) => {
		const data = item.data.value;
		console.log(data);
		currentSkill = { id: data.id, name: data.name, type: data.type.name };
		showSkillModal = true;
	};

	onMount(() => {
		getSkillList().then((response) => {
			const data = response.data;
			skillList = data.skillList;
			edges = data.relationList.map((item) => {
				return { source: item.sourceId - 1, target: item.targetId - 1 };
			});
			flushCategory(data);
			const interval = setInterval(function () {
				if (graphData.length >= skillList.length) {
					interval && clearInterval(interval);
					return;
				}
				const currentPushSkill = skillList[graphData.length];
				tryAddSkills(currentPushSkill);
			}, 500);
		});
	});
</script>

<!-- Modal -->

<svelte:window
	on:dblclick={(item) => {
		console.log(item);

		showSkillModal = true;
	}}
/>
{#if showSkillModal}
	<Modal on:close={closeSKillModal}>
		<svelte:fragment slot="header">Skill</svelte:fragment>
		<svelte:fragment slot="content">
			<label class=" block">
				<span>Name:</span>
				<input bind:value={currentSkill.name} />
			</label>
			<label class=" block">
				<span>Type:</span>
				<input bind:value={currentSkill.type} list="type_list" />
				<datalist id="type_list">
					{#each categories as item (item)}
						<option value={item.name} />
					{/each}
				</datalist>
			</label>
			{#if !currentSkill.id}
				<label class=" block">
					<span>Relation:</span>
					<select bind:value={currentSkill.relation}>
						<option value={null}>None</option>
						{#each graphData as item (item)}
							<option value={item.id}>{item.name}</option>
						{/each}
					</select>
				</label>
			{/if}
		</svelte:fragment>
		<svelte:fragment slot="foot">
			<button class=" btn" on:click={handleSkillChange}> Save </button>
		</svelte:fragment>
	</Modal>
{/if}

<!-- Modal End -->
<Echarts width={'100%'} height={'100%'} {option} onClick={handleSkillPointClick} />
