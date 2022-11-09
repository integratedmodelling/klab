package org.integratedmodelling.authorities.iupac.chebi;

/**
 * Interface to the SQL version of CHEBI, automatically instantiated from the published SQL files and
 * used locally to resolve what is not available in the NIH compound database.
 * 
 * @author Ferd
 *
 */
public class CHEBIService {

    public static final String FTP_URL_PREFIX = "https://ftp.ebi.ac.uk/pub/databases/chebi/generic_dumps/";
    
    
    
    public boolean isOnline() {
        return false;
    }
    
}
