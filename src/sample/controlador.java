package sample;

import animatefx.animation.*;
import com.gembox.spreadsheet.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class controlador implements Initializable {
    static {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    /**
     * FUNCIONES COMUNES CSV
     * */

    public void loadCsv(Event event) throws IOException {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar CSV");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv")
            );
            Node node = (Node) event.getSource();
            final File file = fileChooser.showOpenDialog(node.getScene().getWindow());

            ExcelFile workbook = ExcelFile.load(file.getAbsolutePath());
            ExcelWorksheet worksheet = workbook.getWorksheet(0);
            String[][] sourceData = new String[100][26];
            for (int row = 0; row < sourceData.length; row++) {
                for (int column = 0; column < sourceData[row].length; column++) {
                    ExcelCell cell = worksheet.getCell(row, column);
                    if (cell.getValueType() != CellValueType.NULL)
                        sourceData[row][column] = cell.getValue().toString();
                }
            }
            this.fillTable(sourceData);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    public void saveCsv(Event event) throws IOException {
        ExcelFile file = new ExcelFile();
        ExcelWorksheet worksheet = file.addWorksheet("ALUMNOS-MATERIAS");

        worksheet.getCell(0, 0).setValue("MATERIAS INSCRITAS POR ALUMNO");
        worksheet.getCell(0, 0).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);
        worksheet.getCell(0, 0).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        worksheet.getCell(0, 0).getStyle().getFont().setColor(SpreadsheetColor.fromName(ColorName.WHITE));
        worksheet.getCell(0, 0).getStyle().getFillPattern().setPattern(FillPatternStyle.GRAY_75, SpreadsheetColor.fromName(ColorName.BLACK), SpreadsheetColor.fromName(ColorName.BLACK));
        worksheet.getCells().getSubrange("A1:D1").setMerged(true);
        int i =0;
        int indexRow = 1;
        worksheet.getColumn(0).setWidth(36 * 256);
        worksheet.getColumn(1).setWidth(36 * 256);
        worksheet.getColumn(2).setWidth(36 * 256);
        worksheet.getColumn(3).setWidth(36 * 256);
        for (Alumno alumno: this.listAlumnos) {
            ObservableList<materiasAinscribir> tempMaterias = this.tablasAlumnos.get(i)[1];
            worksheet.getCell(indexRow, 0).setValue("Alumno: "+alumno.name);
            worksheet.getCell(indexRow, 0).getStyle().getBorders().setBorders(MultipleBorders.outside(), SpreadsheetColor.fromArgb(0, 0, 0), LineStyle.THIN);

            indexRow++;
            for (int j = 0; j < 4; j++) {
                worksheet.getCell(indexRow, j).setValue(this.headersMaterias[j]); // Header
                worksheet.getCell(indexRow, j).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);
                worksheet.getCell(indexRow, j).getStyle().getFont().setColor(SpreadsheetColor.fromName(ColorName.BLUE));
                worksheet.getCell(indexRow, j).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
            }

            indexRow+=1;
            for (materiasAinscribir mat: tempMaterias) {
                worksheet.getCell(indexRow, 0).setValue(mat.id.getValue());
                worksheet.getCell(indexRow, 0).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);

                worksheet.getCell(indexRow, 1).setValue(mat.materia.getValue());
                worksheet.getCell(indexRow, 1).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);

                worksheet.getCell(indexRow, 2).setValue(mat.dia.getValue());
                worksheet.getCell(indexRow, 2).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);

                worksheet.getCell(indexRow, 3).setValue(mat.horarios.getValue());
                worksheet.getCell(indexRow, 3).getStyle().setHorizontalAlignment(HorizontalAlignmentStyle.CENTER);
                indexRow++;
            }
            i++;
        }

       // worksheet.getCells().getSubrangeAbsolute(4, 0, 4, 7).setMerged(true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"),
                new FileChooser.ExtensionFilter("ODS files (*.ods)", "*.ods"),
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html")
        );

        Node node = (Node) event.getSource();
        File saveFile = fileChooser.showSaveDialog(node.getScene().getWindow());
        file.save(saveFile.getAbsolutePath());
    }

    void fillTable(String[][] dataSource){
            switch (buttonCsvClicked){
                case 1:
                    for (String[] dataSourceCode:dataSource) {
                        if (dataSourceCode[0]!=null){ // SI LA FILA ESTA NULL QUIERE DECIR QUE YA NO HAY REGISTROS
                            String[] dataAlumno = dataSourceCode[0].split(";"); // SE CONVIERTE A STRING POR MEDIO DE SPLIT ; YA QUE EL ARCHIVO CSV ESTA COMO UTF-8 SEPARADO POR ";":
                            Alumno productoCsv = new Alumno(dataAlumno[1],dataAlumno[0]);
                            this.listAlumnos.add(productoCsv);
                        }
                    }
                    new FadeOutDown(this.btnCargarAlumnosCsv).play();
                    break;
                case 2:
                    for (String[] dataSourceCode:dataSource) {
                        if (dataSourceCode[0]!=null){ // SI LA FILA ESTA NULL QUIERE DECIR QUE YA NO HAY REGISTROS
                            String[] dataAlumno = dataSourceCode[0].split(";"); // SE CONVIERTE A STRING POR MEDIO DE SPLIT ; YA QUE EL ARCHIVO CSV ESTA COMO UTF-8 SEPARADO POR ";":
                            materiasAinscribir materia = new materiasAinscribir(dataAlumno[0],dataAlumno[1],dataAlumno[2],dataAlumno[3]);
                            this.listMateriasAinscribr.add(materia);
                        }
                    }
                    new FadeOutDown(this.btnCargarMateriasCsv).play();
                    this.setAll();
                    new FadeInUp(this.btnExportar).play();
                    break;
            }
    }


    /**
     * LOGIN TODO LOGIC
     */
    @FXML private ComboBox<String> cbAlumnos = new ComboBox<String>();
    private ObservableList<Alumno> listAlumnos = FXCollections.observableArrayList();
    private ObservableList<String> listAlumnosNombres = FXCollections.observableArrayList();
    private Integer indexAlumnoSeleccionado=0;
    private String[] headersMaterias = {"Id materia","Nombre materia","Dia","Horario"};
    @FXML Button btnExportar = new Button();
    @FXML private Button btnIniciarSesion;
    @FXML private Pane paneLogin;
    /**
     * CARGAR CSV
     */

    @FXML
    Button btnCargarAlumnosCsv = new Button();
    @FXML
    Button btnCargarMateriasCsv = new Button();
    int buttonCsvClicked = 0;
    /**
     *  INDICE:
     *  1-) ALUMNOS
     *  2-) MATERIAS
     *  4-) IMPORTAR
     * */
    /**
     * MATERIAS A INSCRIBIR TODO LOGIC
     */
    @FXML private TableView<materiasAinscribir> tableMateriasAinscribir = new TableView<materiasAinscribir>();
    @FXML private TableColumn<materiasAinscribir,String> tcId1 = new TableColumn<materiasAinscribir,String>();
    @FXML private TableColumn<materiasAinscribir,String> tcMateria1 = new TableColumn<materiasAinscribir,String>();
    @FXML private TableColumn<materiasAinscribir,String> tcDia1 = new TableColumn<materiasAinscribir,String>();
    @FXML private TableColumn<materiasAinscribir,String> tcHorario1 = new TableColumn<materiasAinscribir,String>();

    @FXML private TableView<materiasAinscribir> tableMateriasInscritas = new TableView<materiasAinscribir>();
    @FXML private TableColumn<materiasAinscribir,String> tcId2 = new TableColumn<materiasAinscribir,String>();
    @FXML private TableColumn<materiasAinscribir,String> tcMateria2 = new TableColumn<materiasAinscribir,String>();
    @FXML private TableColumn<materiasAinscribir,String> tcDia2 = new TableColumn<materiasAinscribir,String>();
    @FXML private TableColumn<materiasAinscribir,String> tcHorario2 = new TableColumn<materiasAinscribir,String>();

    private Integer materiaSeleccionadaAinscribir = -1;
    private Integer getMateriaSeleccionadaEliminar = -1;
    public ObservableList<materiasAinscribir> listMateriasAinscribr = FXCollections.observableArrayList();
    public ObservableList<materiasAinscribir> listMateriasInscritas = FXCollections.observableArrayList();
    public ObservableList<ObservableList[]> tablasAlumnos = FXCollections.observableArrayList();

    /**
     * TODO LOGIC ANYTHING ELSE
     */
    @FXML private Button btnSalir;
    @FXML private Button btnEliminarMateria;
    void setTables(){
        this.tcId1.setCellValueFactory(id->id.getValue().idProperty());
        this.tcMateria1.setCellValueFactory(materia->materia.getValue().materiaProperty());
        this.tcDia1.setCellValueFactory(dia->dia.getValue().diaProperty());
        this.tcHorario1.setCellValueFactory(horario->horario.getValue().horariosProperty());
        this.tcId2.setCellValueFactory(id->id.getValue().idProperty());
        this.tcMateria2.setCellValueFactory(materia->materia.getValue().materiaProperty());
        this.tcDia2.setCellValueFactory(dia->dia.getValue().diaProperty());
        this.tcHorario2.setCellValueFactory(horario->horario.getValue().horariosProperty());
    }

    ObservableList[] getMaterias(Integer indexAlumno){
        /*for (ObservableList[] alunmo: this.tablasAlumnos) {
            System.out.println("alumno: "+this.listAlumnos.get(indexAlumno).name);
            for (ObservableList<materiasAinscribir> materias: alunmo) {
                for (materiasAinscribir mat :materias) {
                    System.out.println(mat.materiaProperty());
                }
            }
        }*/
        return this.tablasAlumnos.get(indexAlumno);
    }

    /**
     * BOTONES TODO LOGIC
     */
    @FXML private Button btnAgregarMateria;

    @FXML
    void agregarMateria(){
            if(this.materiaSeleccionadaAinscribir>-1){
                materiasAinscribir materia = this.listMateriasAinscribr.get(this.materiaSeleccionadaAinscribir);
                this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[1].add(materia);
                this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[0].remove(this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[0].get(this.materiaSeleccionadaAinscribir));
                this.materiaSeleccionadaAinscribir =-1;
                this.btnAgregarMateria.setDisable(true);
            }
    }
    @FXML
    void eliminarMateria(){
        if(this.getMateriaSeleccionadaEliminar>-1){

            this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[0].add(this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[1].get(this.getMateriaSeleccionadaEliminar));
            this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[1].remove(this.tablasAlumnos.get(this.indexAlumnoSeleccionado)[1].get(this.getMateriaSeleccionadaEliminar));
            this.getMateriaSeleccionadaEliminar =-1;
            this.btnEliminarMateria.setDisable(true);

        }
    }
    @FXML
    void SelectMateriaFromTablaMateriasInscritas(){
        try {
            this.getMateriaSeleccionadaEliminar =this.tableMateriasInscritas.getSelectionModel().getSelectedIndex();
            System.out.println(this.getMateriaSeleccionadaEliminar);
            this.btnEliminarMateria.setDisable(false);
        }catch (Exception e){

        }
    }

    @FXML
    void SelectMateriaFromTablaMateriasAinscribir(){

        try {
            this.materiaSeleccionadaAinscribir =this.tableMateriasAinscribir.getSelectionModel().getSelectedIndex();
            this.btnAgregarMateria.setDisable(false);
        }catch (Exception e){

        }
    }

    void setAll(){
        this.listAlumnos.forEach(alumno -> {
            this.listAlumnosNombres.add(alumno.name);
            ObservableList<materiasAinscribir> materias = FXCollections.observableArrayList();
            ObservableList<materiasAinscribir> materiasAinscribirAlum = FXCollections.observableArrayList(); // esta e una copia de las materias a inscribir
            materiasAinscribirAlum.addAll(this.listMateriasAinscribr);
            ObservableList[] materiass = {materiasAinscribirAlum,materias};
            this.tablasAlumnos.add(materiass);
        });

        this.cbAlumnos.setItems(this.listAlumnosNombres);
    }
    @FXML
    void IniciarSesion(){
        this.indexAlumnoSeleccionado = this.cbAlumnos.getSelectionModel().getSelectedIndex(); // se toma el index del alumno tomado
        if(this.indexAlumnoSeleccionado>-1){
            this.tableMateriasAinscribir.setItems(this.getMaterias(this.indexAlumnoSeleccionado)[0]);
            this.tableMateriasInscritas.setItems(this.getMaterias(this.indexAlumnoSeleccionado)[1]);
            new FadeOutLeft(this.paneLogin).play();
        }else{
            if (buttonCsvClicked==0){ // SI EL BOTON DE ALUMNOS AUN NO HA SIDO TOCADO ENTONCES SE DARA SHAKE A EL BOTON
                new Shake(this.btnCargarAlumnosCsv).play();
            } else if(buttonCsvClicked==1 && this.listAlumnos.size()>0){ // ya fue clickeado alumnos y ya cargo los alumnos
                new Shake(this.btnCargarMateriasCsv).play();
            }else if (buttonCsvClicked==1 && this.listAlumnos.size()==0){ // clickedo alumnos pero no hay almacenados
                new Shake(this.btnCargarAlumnosCsv).play();
            }else if (buttonCsvClicked==2 && this.listMateriasAinscribr.size()==0){ //Clickeo materias pero no cargo el CSV
                new Shake(this.btnCargarMateriasCsv).play();
            }else{
                new Shake(this.cbAlumnos).play();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.btnCargarAlumnosCsv.setOnAction(e->buttonCsvClicked=1);
        this.btnCargarMateriasCsv.setOnAction(e->buttonCsvClicked=2);
        this.btnAgregarMateria.setDisable(true);
        this.btnEliminarMateria.setDisable(true);
        this.setTables();
        new Shake(this.btnCargarAlumnosCsv).play();
    }

    @FXML
    void salir(){
        new FadeInLeft(this.paneLogin).play();
    }
}
