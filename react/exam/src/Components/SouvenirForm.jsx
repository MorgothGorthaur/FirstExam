import {Button, Form} from "react-bootstrap";
import {useState} from "react";
import Input from "../UI/Input/Input";
import ManufacturerService from "../API/ManufacturerService";

const SouvenirForm = ({CreateOrUpdate, id, souvenir}) => {
    const [name, setName] = useState("");
    const [date, setDate] = useState();
    const [price, setPrice] = useState(0);
    const create = (e) => {
        e.preventDefault();
        ManufacturerService.addSouvenir(id, name, price, date).then(data => CreateOrUpdate(data));
    }
    const update = (e) => {

    }
    return (
        <Form onSubmit={souvenir ? update : create}>
            <Input type = "text" placeholder = "name" value = {name} onChange = {e => setName(e.target.value)} />
            <Input type = "date" placeholder = "date" value = {date} onChange = {e => setDate(e.target.value)} />
            <Input type = "double" placeholder = "price" value = {price} onChange = {e => setPrice(e.target.value)} />
            <Button type="submit">{souvenir ? "update" : "create"}</Button>
        </Form>
    );
};
export default SouvenirForm;