// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	PersistenceModule.java
//
// Team 9: Vic Lawson, Justin Rector, Chase Spivey
//
//
//

package dawgtrades.persistence;


import java.util.Iterator;

import dawgTrades.dawgTradesException;
import dawgTrades.model.Attribute;
import dawgTrades.model.Attribute_type;
import dawgTrades.model.Auction;
import dawgTrades.model.Bids;
import dawgTrades.model.Category;
import dawgTrades.model.Item;
import dawgTrades.model.Membership;
import dawgTrades.model.Rating_report;
import dawgTrades.model.RegisteredUser;

public interface Persistence {

  public void                    saveClub( Club club ) throws ClubsException;
  public Iterator<Club>          restoreClub( Club club ) throws ClubsException;
  public void		         deleteClub( Club c ) throws ClubsException;
  
  public void                    savePerson( Person person ) throws ClubsException;
  public Iterator<Person>        restorePerson( Person person ) throws ClubsException;
  public void                    deletePerson( Person person ) throws ClubsException;

  public void                    saveMembership( Membership membership ) throws ClubsException;
  public Iterator<Membership>    restoreMembership( Membership membership ) throws ClubsException;
  public void                    deleteMembership( Membership membership ) throws ClubsException;

  public Person                  restoreEstablishedBy( Club club ) throws ClubsException;
  public Iterator<Club>          restoreEstablishedBy( Person person ) throws ClubsException;
  
};
