function formtString(str, length) {
	const currentLength = str.length;

	if (currentLength > length) {
		return str.substring(0, length);
	}
	for (let i = 0; i < length - currentLength; i++) {
		str = ' ' + str;
	}
	console.log(str);
	return str;
}
const BASE_TIME_FORMATE = 'YYYY-MM-DD';

function getListRandom(item) {
	if (!item) {
		return {};
	}
	return item[new Number((Math.random() * item.length).toFixed(0))];
}

export { formtString, BASE_TIME_FORMATE, getListRandom };
