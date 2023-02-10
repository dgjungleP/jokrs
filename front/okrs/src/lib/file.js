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
export { getFileTree, tryEditFile, getFileById, getFolder };
