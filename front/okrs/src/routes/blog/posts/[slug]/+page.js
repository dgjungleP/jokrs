import { getFileById } from '$lib/file.js';
import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
	const post = await getFileById(params.slug);
	if (!post) {
		throw error(404);
	}

	return { post: post.data };
}
