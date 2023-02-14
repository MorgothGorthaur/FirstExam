import {Button, Form} from "react-bootstrap";
import {useState, useEffect} from "react";
import Input from "../UI/Input/Input";
import ManufacturerService from "../API/ManufacturerService";
import SouvenirService from "../API/SouvenirService";

const SouvenirForm = ({CreateOrUpdate, manufacturerId, souvenir}) => {
    const [id, setId] = useState();
    const [name, setName] = useState("");
    const [date, setDate] = useState();
    const [price, setPrice] = useState(0);
    const create = (e) => {
        e.preventDefault();
        ManufacturerService.addSouvenir(manufacturerId, name, price, date).then(data => data.debugMessage ? alert(data.debugMessage) : CreateOrUpdate(data));
    }
    useEffect(() => {
        if (souvenir) {
            setId(souvenir.id);
            setName(souvenir.name);
            setDate(souvenir.date);
            setPrice(souvenir.price);
        }
    }, []);
    const update = (e) => {
        e.preventDefault();
        SouvenirService.update(id, name, date, price);
        souvenir.name = name;
        souvenir.date = date;
        souvenir.price = price;
        CreateOrUpdate(souvenir);

    }
    return (
        <Form onSubmit={souvenir ? update : create}>
            <Input type="text" placeholder="name" value={name} onChange={e => setName(e.target.value)}/>
            <Input type="date" placeholder="date" value={date} onChange={e => setDate(e.target.value)}/>
            <Input type="double" placeholder="price" value={price} onChange={e => setPrice(e.target.value)}/>
            <Button type="submit">{souvenir ? "update" : "create"}</Button>
        </Form>
    );
};
export default SouvenirForm;