package ma.bankati.dao.creditDao;

import ma.bankati.model.Crediit.Credit;
import ma.bankati.model.Crediit.Status;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CreditDao implements ICreditDao{

    Path path;
    public CreditDao(){
        try{
            this.path = Path.of(
                    getClass()
                            .getClassLoader()
                            .getResource("FileBase/credit.txt")
                            .toURI()
            );
        }catch (Exception e){
            System.err.println("FileBase not found");
        }
    }

    private Credit map(String fileLine){
        String [] fields = fileLine.split("-");
        Long    id          = Long.parseLong(fields[0]);
        Double  mtCredit    = Double.parseDouble(fields[1]);
        Long  nbrMois     = Long.parseLong(fields[2]);
        String  motif       = fields[3].equals("null") ? null : fields[3];
        Status  status ;
        if(fields[4].equals("ACCEPT")){
            status = Status.ACCEPT;
        }else if(fields[4].equals("REFUSE")){
            status = Status.REFUSE;
        }else if(fields[4].equals("ENCOURS")){
            status = Status.ENCOURS;
        }else{
            status = null;
        }

        return new Credit(id, mtCredit, nbrMois, motif, status);
    }

    public String mapToFileLine(Credit credit){

        Long id         = credit.getId();
        Double mtCredit = credit.getMtCredit();
        Long nbrMois    = credit.getNbrMois();
        String motif    = credit.getMotif() == null ? "null" : credit.getMotif();
        String status   = credit.getStatus() == null ? "null" : credit.getStatus().name();

        return id+"-"+mtCredit+"-"+nbrMois+"-"+motif+"-"+status+System.lineSeparator();
    }

    private long newMaxId(){
        return findAll().stream().mapToLong(Credit::getId).max().orElse(0) + 1;
    }

    @Override
    public Credit findById(Long identity) {
        return findAll().stream()
                .filter(u ->   u.getId().equals(identity))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Credit> findAll() {
        try{
            return
                    Files.readAllLines(path)
                            .stream()
                            .skip(1)
                            .map(line -> map(line))
                            .toList();
        }catch (IOException e){
            System.err.println("Error lors de la recuperation des credits");
            return new ArrayList<>();
        }
    }

    @Override
    public Credit save(Credit newElement) {
        try{
            newElement.setId(newMaxId());
            Files.writeString(path, mapToFileLine(newElement), StandardOpenOption.APPEND);
            return newElement;
        }catch (IOException e){
            System.err.println("Error lors de l'enregistrement à la Base de données");
            return null;
        }
    }

    @Override
    public void delete(Credit element) {
        deleteById(element.getId());
    }

    @Override
    public void deleteById(Long identity) {
        List<Credit> updatedList = findAll().stream()
                .filter(u -> !u.getId().equals(identity))
                .toList();
        rewriteFile(updatedList);
        System.out.println("delete methode Dao");
    }

    @Override
    public void update(Credit newValuesElement) {
        List<Credit> updatedList = findAll().stream()
                .map(credit -> credit.getId().equals(newValuesElement.getId()) ? newValuesElement  : credit)
                .toList();
        rewriteFile(updatedList);
    }


    public void rewriteFile(List<Credit> credits){
        try{
            List<String> lines = new ArrayList<>();
            lines.add("ID-MtCredit-NbrMois-Motif-Status"); // Header
            for(Credit credit : credits){
                lines.add(mapToFileLine(credit).trim());
            }
            Files.write(path, lines);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
