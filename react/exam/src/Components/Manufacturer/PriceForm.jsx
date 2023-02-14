import {useState} from "react";
import {Button, Form} from "react-bootstrap";
import Input from "../../UI/Input/Input";
import ManufacturerService from "../../API/ManufacturerService";
import data from "bootstrap/js/src/dom/data";

const PriceForm = ({getCheapest}) => {
    const [price, setPrice] = useState(0);
    const getManufacturers = (e) => {
        e.preventDefault();
        ManufacturerService.getManufacturersCheaperThenValue(price).then(data => getCheapest(data));
    }
    return (
        <Form onSubmit={getManufacturers} >
            <Input type = "double" placeholder = "price" value={price} onChange={e => setPrice(e.target.value)}/>
            <Button type="submit">{"find"}</Button>
        </Form>
    )
};
export default PriceForm;