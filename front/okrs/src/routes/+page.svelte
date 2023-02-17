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
	onMount(() => {
		fresh();
	});

	var option;

	const colors = ['#FFAE57', '#FF7853', '#EA5151', '#CC3F57', '#9A2555'];
	const bgColor = '#2E2733';
	const itemStyle = {
		star5: {
			color: colors[0]
		},
		star4: {
			color: colors[1]
		},
		star3: {
			color: colors[2]
		},
		star2: {
			color: colors[3]
		}
	};
	const inner = [
		{
			name: '虚构',
			itemStyle: {
				color: colors[1]
			},
			children: [
				{
					name: '小说',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '疼'
								},
								{
									name: '慈悲'
								},
								{
									name: '楼下的房客'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: '虚无的十字架'
								},
								{
									name: '无声告白'
								},
								{
									name: '童年的终结'
								}
							]
						},
						{
							name: '3☆',
							children: [
								{
									name: '疯癫老人日记'
								}
							]
						}
					]
				},
				{
					name: '其他',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '纳博科夫短篇小说全集'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: '安魂曲'
								},
								{
									name: '人生拼图版'
								}
							]
						},
						{
							name: '3☆',
							children: [
								{
									name: '比起爱你，我更需要你'
								}
							]
						}
					]
				}
			]
		},
		{
			name: '非虚构',
			itemStyle: {
				color: colors[2]
			},
			children: [
				{
					name: '设计',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '无界面交互'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: '数字绘图的光照与渲染技术'
								},
								{
									name: '日本建筑解剖书'
								}
							]
						},
						{
							name: '3☆',
							children: [
								{
									name: '奇幻世界艺术\n&RPG地图绘制讲座'
								}
							]
						}
					]
				},
				{
					name: '社科',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '痛点'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: '卓有成效的管理者'
								},
								{
									name: '进化'
								},
								{
									name: '后物欲时代的来临'
								}
							]
						},
						{
							name: '3☆',
							children: [
								{
									name: '疯癫与文明'
								}
							]
						}
					]
				},
				{
					name: '心理',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '我们时代的神经症人格'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: '皮格马利翁效应'
								},
								{
									name: '受伤的人'
								}
							]
						},
						{
							name: '3☆'
						},
						{
							name: '2☆',
							children: [
								{
									name: '迷恋'
								}
							]
						}
					]
				},
				{
					name: '居家',
					children: [
						{
							name: '4☆',
							children: [
								{
									name: '把房子住成家'
								},
								{
									name: '只过必要生活'
								},
								{
									name: '北欧简约风格'
								}
							]
						}
					]
				},
				{
					name: '绘本',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '设计诗'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: '假如生活糊弄了你'
								},
								{
									name: '博物学家的神秘动物图鉴'
								}
							]
						},
						{
							name: '3☆',
							children: [
								{
									name: '方向'
								}
							]
						}
					]
				},
				{
					name: '哲学',
					children: [
						{
							name: '4☆',
							children: [
								{
									name: '人生的智慧'
								}
							]
						}
					]
				},
				{
					name: '技术',
					children: [
						{
							name: '5☆',
							children: [
								{
									name: '代码整洁之道'
								}
							]
						},
						{
							name: '4☆',
							children: [
								{
									name: 'Three.js 开发指南'
								}
							]
						}
					]
				}
			]
		}
	];
	for (let j = 0; j < inner.length; ++j) {
		let level1 = inner[j].children;
		for (let i = 0; i < level1.length; ++i) {
			let block = level1[i].children;
			let bookScore = [];
			let bookScoreId;
			for (let star = 0; star < block.length; ++star) {
				let style = (function (name) {
					switch (name) {
						case '5☆':
							bookScoreId = 0;
							return itemStyle.star5;
						case '4☆':
							bookScoreId = 1;
							return itemStyle.star4;
						case '3☆':
							bookScoreId = 2;
							return itemStyle.star3;
						case '2☆':
							bookScoreId = 3;
							return itemStyle.star2;
					}
				})(block[star].name);
				block[star].label = {
					color: style.color,
					downplay: {
						opacity: 0.5
					}
				};
				if (block[star].children) {
					style = {
						opacity: 1,
						color: style.color
					};
					block[star].children.forEach(function (book) {
						book.value = 1;
						book.itemStyle = style;
						book.label = {
							color: style.color
						};
						let value = 1;
						if (bookScoreId === 0 || bookScoreId === 3) {
							value = 5;
						}
						if (bookScore[bookScoreId]) {
							bookScore[bookScoreId].value += value;
						} else {
							bookScore[bookScoreId] = {
								color: colors[bookScoreId],
								value: value
							};
						}
					});
				}
			}
			level1[i].itemStyle = {
				color: inner[j].itemStyle.color
			};
		}
	}
	option = {
		backgroundColor: bgColor,
		color: colors,
		series: [
			{
				type: 'sunburst',
				center: ['50%', '48%'],
				data: inner,
				sort: function (a, b) {
					if (a.depth === 1) {
						return b.getValue() - a.getValue();
					} else {
						return a.dataIndex - b.dataIndex;
					}
				},
				label: {
					rotate: 'radial',
					color: bgColor
				},
				itemStyle: {
					borderColor: bgColor,
					borderWidth: 2
				},
				levels: [
					{},
					{
						r0: 0,
						r: 40,
						label: {
							rotate: 0
						}
					},
					{
						r0: 40,
						r: 105
					},
					{
						r0: 115,
						r: 140,
						itemStyle: {
							shadowBlur: 2,
							shadowColor: colors[2],
							color: 'transparent'
						},
						label: {
							rotate: 'tangential',
							fontSize: 10,
							color: colors[0]
						}
					},
					{
						r0: 140,
						r: 145,
						itemStyle: {
							shadowBlur: 80,
							shadowColor: colors[0]
						},
						label: {
							position: 'outside',
							textShadowBlur: 5,
							textShadowColor: '#333'
						},
						downplay: {
							label: {
								opacity: 0.5
							}
						}
					}
				]
			}
		]
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
