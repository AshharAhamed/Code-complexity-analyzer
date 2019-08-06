/*
-------------------------------------------------------------------------------------------------------
--  Date        Sign    History
--  ----------  ------  --------------------------------------------------------------------------------
--  2019-08-06  Sathira  185817, Created CTC Service Interface.
--  ----------  ------  --------------------------------------------------------------------------------
*/

package com.neo.codecomplexityanalyzer.service.serviceImpl;

public interface CTCService {
    int getControlScore();
    int getIterativeControlScore();
    int getCatchScore();
    int getSwitchScore();
}
