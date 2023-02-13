export default class ManufacturerService {
    static async getAll() {
        try {
            const response = await fetch('http://localhost:8080/exam/manufacturers');
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }

    static async addManufacturer(name, country) {
        try {
            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "name": name,
                    "country": country
                })
            }
            const response = await fetch('http://localhost:8080/exam/manufacturers', requestOptions);
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }

    static async removeManufacturer(id) {
        try {
            const requestOptions = {
                method: 'DELETE',
            };
            const response = await fetch('http://localhost:8080/exam/manufacturers/' + id, requestOptions);
            if(response.status !== 200) alert(await response.json().debugMeddage);
        } catch (e) {
            alert(e);
        }
    }
}