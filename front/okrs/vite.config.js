import { sveltekit } from '@sveltejs/kit/vite';

const config = {
	envDir: './env',
	envPrefix: 'J_',
	plugins: [sveltekit()]
};

export default config;
