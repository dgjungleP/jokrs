import axios from 'axios';

const BASE_HOST = `${import.meta.env.J_BASE_ROUTE}/jokrs`;

async function getObjectives() {
	return axios.get(`${BASE_HOST}/all/objectives`);
}
async function getActionList() {
	return axios.get(`${BASE_HOST}/action/list`);
}
async function updateObjective(item) {
	return axios.post(`${BASE_HOST}/objective`, item);
}

async function updateKeyResult(item) {
	return axios.post(`${BASE_HOST}/key-result`, item);
}
async function getKeyResultByid(keyresultId) {
	return axios.get(`${BASE_HOST}/key-result/${keyresultId}`);
}
async function deleteObjective(objectiveId) {
	return axios.delete(`${BASE_HOST}/objective?objectiveId=${objectiveId}`);
}
async function getKeyResultList(objectiveId) {
	return axios.get(`${BASE_HOST}/all/key-result?objectiveId=${objectiveId}`);
}
export {
	getObjectives,
	updateObjective,
	getKeyResultList,
	deleteObjective,
	updateKeyResult,
	getKeyResultByid,
	getActionList
};
