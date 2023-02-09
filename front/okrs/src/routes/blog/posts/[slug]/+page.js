import { posts } from '$lib/data/posts';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const post = posts.find((post) => post.slug == params.slug);
	if (!post) {
		throw error(404);
	}

	return { post, posts };
}
