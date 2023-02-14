export default class SouvenirService {
    static async getAll() {
        try {
            const response = await fetch('http://localhost:8080/exam/souvenirs');
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }
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

    static async update(id, name, date, price) {
        try {
            const requestOptions = {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    "id": id,
                    "name": name,
                    "date": date,
                    "price": price
                })
            };
            const response = await fetch('http://localhost:8080/exam/souvenirs', requestOptions);
            if (response.status !== 200) alert(await response.json().debugMeddage);
        } catch (e) {
            alert(e);
        }
    }
}