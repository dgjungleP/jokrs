import axios from 'axios';

const BASE_HOST = `${import.meta.env.J_BASE_ROUTE}/home`;

async function getTodoList() {
	return axios.get(`${BASE_HOST}/todo/list`);
}
async function applyTodo(item) {
	return axios.post(`${BASE_HOST}/todo`, item);
}
async function getCountDownList() {
	return axios.get(`${BASE_HOST}/count-down/list`);
}
export { getTodoList, applyTodo, getCountDownList };
