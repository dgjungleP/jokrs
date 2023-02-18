import { data } from '$lib/data/sunburst.js';

/** @type {import('./$types').PageLoad} */
export async function load() {
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

	return { bgColor, itemStyle, data, colors };
}
