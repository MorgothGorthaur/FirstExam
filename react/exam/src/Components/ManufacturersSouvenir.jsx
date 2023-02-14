import {useState} from "react";
import {useEffect} from "react";
import ManufacturerService from "../API/ManufacturerService";
import Loader from "../UI/Loader/Loader";
import {Button, Modal} from "react-bootstrap";
import SouvenirForm from "./SouvenirForm";

const ManufacturersSouvenir = ({id}) => {
    const [souvenirs, setSouvenirs] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            ManufacturerService.getManufacturersSouvenirs(id).then(data => setSouvenirs(data));
            setLoading(false);
        }, 1000);
    }, []);
    const add = (souvenir) => {
        setSouvenirs([...souvenirs, souvenir]);
        setModal(false);
    }
    return (
        <div>
            {
                loading ? (
                    <Loader/>
                ) : (
                    <div>
                        {
                            souvenirs.length ? (
                                <div>
                                    souvenirs.map(souvenir => {
                                    <h1> gg</h1>
                                })
                                </div>
                            ) : (
                                <h1> souvenirs not founded! </h1>
                            )
                        }
                        <Button onClick={() => setModal(true)}> add </Button>
                        <Modal show={modal} onHide={setModal}> <SouvenirForm CreateOrUpdate={add} id = {id}/> </Modal>
                    </div>
                )
            }
        </div>
    );
};
export default ManufacturersSouvenir;