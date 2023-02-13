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
                body: JSON.stringify(name, country)
            }
            const response = await fetch('http://localhost:8080/exam/manufacturers');
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }
}