import axios from 'axios';

const BASE_HOST = `${import.meta.env.J_BASE_ROUTE}/file`;

async function getFileTree() {
	return axios.get(`${BASE_HOST}/tree`);
}
async function getFolder() {
	return axios.get(`${BASE_HOST}/folder`);
}
async function tryEditFile(item) {
	return axios.post(`${BASE_HOST}/try/add`, item);
}
async function getFileById(id) {
	return axios.get(`${BASE_HOST}?id=${id}`);
}
async function getBlogList() {
	return axios.get(`${BASE_HOST}/blog/list`);
}
export { getFileTree, tryEditFile, getFileById, getFolder, getBlogList };
