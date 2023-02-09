import axios from 'axios';

const BASE_HOST = `${import.meta.env.J_BASE_ROUTE}/skill`;

async function getSkillList() {
	return axios.get(`${BASE_HOST}/list`);
}
async function addSkill(item) {
	return axios.post(`${BASE_HOST}/add`, item);
}

export { getSkillList, addSkill };
