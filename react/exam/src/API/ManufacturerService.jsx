export default class ManufacturerService {
    static async getAll() {
        try {
            const response = await fetch('http://localhost:8080/exam/manufacturers');
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }
}