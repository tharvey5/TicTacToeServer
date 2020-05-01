package edu.saddleback.cs4b.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

public class PlayersController
{
    @FXML
    Button refreshButton;

    @FXML
    private TableView playersTable;

    @FXML
    public void handleRefreshAction()
    {
//        @FXML
//        public void refresh() {
//
//        Task<List<Participant>> task = new Task<List<Participant>>() {
//            @Override
//            protected List<Participant> call() throws Exception {
//                return fetchData();
//            }
//
//            @Override
//            protected void succeeded() {
//                tblParticipants.getItems().clear();
//                tblParticipants.getItems().addAll( getValue() );
//            }
//        };
//
//        new Thread(task).start();
//    }
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'REFRESH' BUTTON WILL CHANGE COLOR WHEN THE MOUSE IS HOVERING OVER IT
     */
    @FXML
    public void highlightRefresh()
    {
        refreshButton.setOnMouseEntered(mouseEvent -> refreshButton.setTextFill(Color.valueOf("#FFD700")));
    }

    /**
     * WHEN THIS METHOD IS CALLED THE 'REFRESH' BUTTON WILL CHANGE BACK TO THE DEFAULT TEXT COLOR WHEN THE MOUSE IS
     * NO LONGER HOVERING OVER IT
     */
    @FXML
    public void resetRefresh()
    {
        refreshButton.setOnMouseExited(mouseEvent -> refreshButton.setTextFill(Color.BLACK));
    }

}
