import {Button, Modal} from "react-bootstrap";
import {useState} from "react";
import SouvenirForm from "../SouvenirForm";

const Souvenir = ({souvenir, remove, update}) => {
    const [modal, setModal] = useState(false);
    const change = (data) => {
        setModal(false);
        update(data);
    }
    return (
        <div>
            <h1> {souvenir.name} </h1>
            <h1> {souvenir.date} </h1>
            <h1> {souvenir.price} </h1>
            <Button variant={"danger"} onClick={() => remove(souvenir.id)}> remove </Button>
            <Button onClick={() => setModal(true)}> update</Button>
            <Modal show={modal} onHide={setModal}> <SouvenirForm CreateOrUpdate={change} souvenir = {souvenir} /> </Modal>
        </div>
    );
};
export default Souvenir;