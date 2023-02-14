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
            if (response.status !== 200) alert(await response.json().debugMeddage);
        } catch (e) {
            alert(e);
        }
    }

    static async updateManufacturer(id, name, country) {
        try {
            const requestOptions = {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    "id": id,
                    "name": name,
                    "country": country
                })
            };
            const response = await fetch('http://localhost:8080/exam/manufacturers', requestOptions);
            if (response.status !== 200) alert(await response.json().debugMeddage);
        } catch (e) {
            alert(e);
        }
    }

    static async getManufacturersCheaperThenValue(price) {
        try {
            const response = await fetch('http://localhost:8080/exam/manufacturers/cheaper/' + price );
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }

    static async getManufacturersSouvenirs(id) {
        try {
            const response = await fetch('http://localhost:8080/exam/manufacturers/' + id );
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }

    static async addSouvenir(id, name, price, date) {
        try{
            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "name": name,
                    "price": price,
                    "date" : date
                })
            }
            const response = await fetch('http://localhost:8080/exam/manufacturers/' + id, requestOptions);
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }
}