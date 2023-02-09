import { posts } from '$lib/data/posts';
/** @type {import('./$types').LayoutLoad} */
export async function load() {
	return { posts };
}
