import {Button, Form} from 'react-bootstrap';
import Input from "../../UI/Input/Input";
import {useState, useEffect} from "react";
import ManufacturerService from "../../API/ManufacturerService";
import data from "bootstrap/js/src/dom/data";

const ManufacturerForm = ({CreateOrUpdate, manufacturer}) => {
    const [id,setId] = useState();
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const update = (e) => {
        e.preventDefault();
        ManufacturerService.updateManufacturer(id, name, country);
        manufacturer.name = name;
        manufacturer.country = country;
        CreateOrUpdate(manufacturer);
    }
    const add = (e) => {
        e.preventDefault();
        ManufacturerService.addManufacturer(name, country).then(data => {
            data.debugMessage ? alert(data.debugMessage) : CreateOrUpdate(data);
        });
    }

    useEffect(() => {
        if(manufacturer) {
            setId(manufacturer.id);
            setName(manufacturer.name);
            setCountry(manufacturer.country);
        }
    },[]);

    return (<Form onSubmit={manufacturer ? update : add}>
            <Input type="text" placeholder="name" value={name} onChange={e => setName(e.target.value)}></Input>
            <Input type="text" placeholder="country" value={country} onChange={e => setCountry(e.target.value)}></Input>
            <Button type="submit">{manufacturer ? "update" : "create"}</Button>
        </Form>);
};
export default ManufacturerForm;