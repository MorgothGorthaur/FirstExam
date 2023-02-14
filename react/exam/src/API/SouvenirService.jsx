export default class SouvenirService {
    static async remove(id) {
        try {
            const requestOptions = {
                method: 'DELETE',
            };
            const response = await fetch('http://localhost:8080/exam/souvenirs/' + id, requestOptions);
            if (response.status !== 200) alert(await response.json().debugMeddage);
        } catch (e) {
            alert(e);
        }
    }
}