import {Button, Form} from 'react-bootstrap';
import Input from "../UI/Input/Input";
import {useState} from "react";
import ManufacturerService from "../API/ManufacturerService";

const ManufacturerForm = ({CreateOrUpdate, manufacturer}) => {
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const update = (e) => {
        e.preventDefault();
    }
    const add = (e) => {
        e.preventDefault();
        ManufacturerService.addManufacturer(name, country).then(data => {
            validation(data);
        });
    }

    const validation = (data) => {
        data.debugMessage ? alert(data.debugMessage) : CreateOrUpdate(data);
    }
    return (<Form onSubmit={manufacturer ? update : add}>
            <Input type="text" placeholder="name" value={name} onChange={e => setName(e.target.value)}></Input>
            <Input type="text" placeholder="country" value={country} onChange={e => setCountry(e.target.value)}></Input>
            <Button type="submit">{manufacturer ? "update" : "create"}</Button>
        </Form>);
};
export default ManufacturerForm;