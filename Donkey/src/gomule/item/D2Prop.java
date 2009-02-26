package gomule.item;

import randall.d2files.D2TblFile;
import randall.d2files.D2TxtFile;

public class D2Prop {

	private int[] pVals;
	private int pNum;
	private int funcN;
	private int qFlag;
	private boolean opApplied;
	//Quality flag is there to seperate different types of items
	//0 = ordinary item
	//1 = ???
	//2 = Set 2 items
	//3 = Set 3 items
	//4 = Set 4 items
	//5 = Set 5 items
	//6 = Set ? Items
	//7 = Rune/Gem weapons
	//8 = Rune/Gem armor
	//9 = Rune/Gem shields



	public D2Prop(int pNum, int[] pVals, int qFlag){

		this.pNum = pNum;
		this.pVals = pVals;
		this.qFlag = qFlag;
	}

	public D2Prop(D2Prop newProp) {
		this.pNum = newProp.getPNum();
		this.pVals = newProp.getPVals();
		this.qFlag = 0;
	}


	public D2Prop(int pNum, int[] pVals, int qFlag, boolean opApplied, int funcN) {


		this.pNum = pNum;
		this.pVals = pVals;
		this.qFlag = qFlag;
		this.opApplied = opApplied;
		this.funcN = funcN;
	}

	public void setPNum(int pNum){
		this.pNum = pNum;
	}

	public void setPVals(int[] pVals){
		this.pVals = pVals;
	}

	public int getQFlag() {
		return qFlag;
	}

	public void setFuncN(int funcN){
		this.funcN = funcN;
	}

	public int[] getPVals() {

		return pVals;
	}

	public int getPNum(){

		return pNum;
	}

	public int getFuncN(){
		return funcN;
	}

	public void modifyVals(int funcN, int[] pVals){

		this.funcN = funcN;
		this.pVals = pVals;

	}




	public String generateDisplay(int qFlag, int cLvl) {


		if(this.qFlag != qFlag){
			return null;
		}

		String oString = D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstrpos"));

		//FUNCTION 0 means that you should use the txt files to find the print function to use. Otherwise, it should be a case of looking for custom funcs
		if(funcN == 0){

			if( D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descfunc") != null && ! D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descfunc").equals("")){
				funcN = Integer.parseInt( D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descfunc"));
			}
		}

		int dispLoc = 1;
		try{
			dispLoc= Integer.parseInt(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descval"));
		}catch(NumberFormatException e){
			//leave dispLoc as  = 1
		}

		//Max Durability
		if (pNum == 73){
			oString = "Maximum Durability";
			funcN =1;
		}else if(pNum == 92){
			oString = "Required Level";
			funcN = 1;
			dispLoc = 2;	
		}else if(pNum == 356){
			funcN = 40;
		}else if(pNum == 26){
			oString = "Replenishes Mana";
			funcN = 1;
			dispLoc = 2;
		}

		switch(funcN){

		case(1):
			if(dispLoc == 1){
				if(oString.contains("%d")){
					return oString.replace("%d", Integer.toString(pVals[0]));	
				}else{
					if(pVals[0] > -1){
						return "+" + pVals[0]+ " " + oString;
					}else{
						return pVals[0]+ " " + oString;
					}
				}
			}else if(dispLoc == 2){

				if(pVals[0] > -1){
					return oString + " +" + pVals[0];
				}else{
					return oString + " " + pVals[0];
				}


			}else{
				return oString;
			}
		case(2):
			if(dispLoc == 1){
				return pVals[0] + "% " + oString;
			}else if(dispLoc == 2){
				return oString + " " + pVals[0] + "%" ;
			}else{
				return oString;
			}

		case(3):
			if(dispLoc == 1){
				return  pVals[0] + " " + oString;

			}else if(dispLoc == 2){
				return   oString  + " " + pVals[0];
			}else{
				return oString;
			}

		case(4):
			if(dispLoc == 1){

				if(pVals[0] > -1){
					return "+" + pVals[0]+"% " + oString;
				}else{
					return pVals[0]+"% " + oString;
				}



			}else if(dispLoc == 2){

				if(pVals[0] > -1){
					return oString + " +" + pVals[0]+"%" ;
				}else{
					return oString + " " +  pVals[0]+"%" ;
				}

			}else{
				return oString;
			}

		case(5):
			if(dispLoc == 1){
				return ((pVals[0]*100)/128) +"% " + oString;

			}else if(dispLoc == 2){
				return oString + " " + ((pVals[0]*100)/128) +"%" ;
			}else{
				return oString;
			}

		case(6):
			if(dispLoc == 1){
				return "+" + pVals[0]+" " + oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2"));

			}else if(dispLoc == 2){
				return oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2")) + " +" + pVals[0];
			}else{
				return oString;
			}

		case(7):
			if(dispLoc == 1){
				return pVals[0]+"% " + oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2"));

			}else if(dispLoc == 2){
				return oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2")) + pVals[0]+"%";
			}else{
				return oString;
			}

		case(8):
			if(dispLoc == 1){
				return "+" + pVals[0]+"% " + oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2"));

			}else if(dispLoc == 2){
				return  oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2")) + " +" + pVals[0]+"%";
			}else{
				return oString;
			}

		case(9):
			if(dispLoc == 1){
				return pVals[0]+" " + oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2"));

			}else if(dispLoc == 2){
				return oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2")) + " " + pVals[0];
			}else{
				return oString;
			}

		case(10):
			if(dispLoc == 1){
				return (pVals[0]*100)/128 +"% " + oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2"));

			}else if(dispLoc == 2){
				return oString + " " + D2TblFile.getString(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descstr2")) +  (pVals[0]*100)/128 +"%";
			}else{
				return oString;
			}

		case(11):

			return "Repairs 1 Durability in " + (100/pVals[0]) + " Seconds";

		case(12):
			if(dispLoc == 1){
				return "+" + pVals[0]+" " + oString;

			}else if(dispLoc == 2){
				return oString + " +" + pVals[0];
			}else{
				return oString;
			}

		case(13):

			return "+" + pVals[1] + " to " + D2TxtFile.getCharacterCode(pVals[0]) + " Skill Levels";

		case(14):

			return "+" + pVals[1] + " to " + getSkillTree(pVals[0]);

		case(15):

			oString = oString.replaceFirst("%d%", Integer.toString(pVals[2]));
		oString = oString.replace("%d", Integer.toString(pVals[0]));
		return oString.replace("%s", D2TblFile.getString(D2TxtFile.SKILL_DESC.searchColumns("skilldesc",D2TxtFile.SKILLS.getRow(pVals[1]).get("skilldesc")).get("str name")));

		case(16):

			oString = oString.replace("%d", Integer.toString(pVals[1]));
		return oString.replace("%s", D2TblFile.getString(D2TxtFile.SKILL_DESC.searchColumns("skilldesc",D2TxtFile.SKILLS.getRow(pVals[0]).get("skilldesc")).get("str name")));


		case(17):

			return "By time!? Oh shi....";


		case(18):
			return "By time!? Oh shi....";

		case(20):
			if(dispLoc == 1){
				return (pVals[0] * -1) + "% " + oString;
			}else if(dispLoc == 2){
				return  oString + " " +  (pVals[0] * -1) + "%";
			}else{
				return oString;
			}


		case(21):
			if(dispLoc == 1){
				return (pVals[0] * -1) + " " + oString;
			}else if(dispLoc == 2){
				return  oString + " " +  (pVals[0] * -1);
			}else{
				return oString;
			}

		case(23):

			return pVals[1] + "% " + oString + " " +  D2TblFile.getString(D2TxtFile.MONSTATS.getRow(pVals[0]).get("NameStr"));				

		case(24):

			oString = oString.replaceFirst("%d", Integer.toString(pVals[2]));
		oString = oString.replace("%d", Integer.toString(pVals[3]));
		return "Level " + pVals[0] + " " + D2TblFile.getString(D2TxtFile.SKILL_DESC.searchColumns("skilldesc",D2TxtFile.SKILLS.getRow(pVals[1]).get("skilldesc")).get("str name")) + " " + oString;

		case(27):
			return "+" + pVals[1] + " to " + D2TblFile.getString(D2TxtFile.SKILL_DESC.searchColumns("skilldesc",D2TxtFile.SKILLS.getRow(pVals[0]).get("skilldesc")).get("str name")) + " " + D2TblFile.getString((D2TxtFile.SKILLS.getRow(D2TxtFile.SKILL_DESC.getRow(pVals[0]).getRowNum()).get("charclass").charAt(0) + "").toUpperCase() + D2TxtFile.SKILLS.getRow(D2TxtFile.SKILL_DESC.getRow(pVals[0]).getRowNum()).get("charclass").substring(1) + "Only");

		case(28):

			return "+" + pVals[1] + " to " + D2TblFile.getString(D2TxtFile.SKILL_DESC.searchColumns("skilldesc",D2TxtFile.SKILLS.getRow(pVals[0]).get("skilldesc")).get("str name"));


		//UNOFFICIAL PROPERTIES

		//Enhanced Damage
		case(30):

			return pVals[0] + "% Enhanced Damage";

		case(31):

			return "Adds " + pVals[0] + " - " + pVals[1] + " Damage";

		case(32):

			return "Adds " + pVals[0] + " - " + pVals[1] + " Fire Damage";

		case(33):

			return "Adds " + pVals[0] + " - " + pVals[1] + " Lightning Damage";

		case(34):

			return "Adds " + pVals[0] + " - " + pVals[1] + " Magic Damage";

		case(35):

			if(pVals[0] == pVals[1]){
				return "Adds " + pVals[0] + " Cold Damage Over " + Math.round((double)pVals[2]/25.0) + " Secs (" + pVals[2] + " Frames)";	
			}

		return "Adds " + pVals[0] + " - " + pVals[1] + " Cold Damage Over " + Math.round((double)pVals[2]/25.0) + " Secs (" + pVals[2] + " Frames)";

		case(36):

			if(pVals.length == 4){

				if(pVals[0] == pVals[1]){
					return "Adds " + Math.round(pVals[0]*((double)pVals[2]/(double)pVals[3])/256)  + " Poison Damage Over " + (int)Math.floor(((double)pVals[2]/(double)pVals[3])/25.0) + " Secs (" + pVals[2] + " Frames)";
				}

				return "Adds " + Math.round(pVals[0]*((double)pVals[2]/(double)pVals[3])/256) + " - " + Math.round(pVals[1]*((double)pVals[2]/(double)pVals[3])/256) + " Poison Damage Over " + (int)Math.floor(((double)pVals[2]/(double)pVals[3])/25.0) + " Secs (" + pVals[2] + " Frames)";

			}else{

				if(pVals[0] == pVals[1]){
					return "Adds " + Math.round(pVals[0]*(double)pVals[2]/256)  + " Poison Damage Over " + (int)Math.floor((double)pVals[2]/25.0) + " Secs (" + pVals[2] + " Frames)";
				}

				return "Adds " + Math.round(pVals[0]*(double)pVals[2]/256) + " - " + Math.round(pVals[1]*(double)pVals[2]/256) + " Poison Damage Over " + (int)Math.floor((double)pVals[2]/25.0) + " Secs (" + pVals[2] + " Frames)";
			}
		case (37):

			return "All Resistances +" + pVals[0];

		case (38):

			return "All Stats +" + pVals[0];

		case (39):

			return "Level " + pVals[1] + " " + D2TxtFile.getCharacterCode(pVals[0]);

		case (40):

			switch (pVals[0]){

			case 0:
				return "Found In Normal Difficulty";
			case 1:
				return "Found In Nightmare Difficulty";
			case 2:
				return "Found In Hell Difficulty";
			}
		}

		return "Unrecognized property: " + this.pNum;
	}

	public void applyOp(int cLvl) {

		if(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("op").equals(""))return;
		if(opApplied)return;

		int op = Integer.parseInt(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("op"));

		switch(op){

		case (2):
		case (4):
		case (5):

			if(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("op base").equals("level")){

				pVals[0] = (int)Math.floor(((double)(pVals[0] * cLvl)) / ((double)(Math.pow(2, Integer.parseInt(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("op param")))))); 
			}
		}
		opApplied = true;
	}

	public String getSkillTree(int lSkillNr){

		switch (lSkillNr)
		{
		case 0:
			return "Bow and Crossbow Skills (Amazon Only)";

		case 1:
			return "Passive and Magic Skills (Amazon Only)";

		case 2:
			return "Javelin and Spear Skills (Amazon Only)";

		case 8:
			return "Fire Skills (Sorceress Only)";

		case 9:
			return "Lightning Skills (Sorceress Only)";

		case 10:
			return "Cold Skills (Sorceress Only)";

		case 16:
			return "Curses (Necromancer only)";

		case 17:
			return "Poison and Bone Skills (Necromancer Only)";

		case 18:
			return "Summoning Skills (Necromancer Only)";

		case 24:
			return "Combat Skills (Paladin Only)";

		case 25:
			return "Offensive Aura Skills (Paladin Only)";

		case 26:
			return "Defensive Aura Skills (Paladin Only)";

		case 32:
			return "Combat Skills (Barbarian Only)";

		case 33:
			return "Masteries Skills (Barbarian Only)";

		case 34:
			return "Warcry Skills (Barbarian Only)";

		case 40:
			return "Summoning Skills (Druid Only)";

		case 41:
			return "Shape-Shifting Skills (Druid Only)";

		case 42:
			return "Elemental Skills (Druid Only)";

		case 48:
			return "Trap Skills (Assassin Only)";

		case 49:
			return "Shadow Discipline Skills (Assassin Only)";

		case 50:
			return "Martial Art Skills (Assassin Only)";

		}
		return "Unknown Tree (P 188)";

	}

	public void addPVals(int[] newVals) {

//		Poison length needs to keep track of the number of properties contributing to it. 
//		Therefore, [2] becomes the counter.
		if(getPNum() == 59){

			if(pVals[2] == 0){
				pVals  = new int[]{pVals[0], pVals[1], 1};
			}

			if(newVals[2] == 0){
				newVals  = new int[]{newVals[0], newVals[1], 1};
			}
		}

		int vLen = pVals.length;

		if(pVals.length > newVals.length){
			vLen = newVals.length;
		}

		for(int z = 0;z<vLen;z++){
			pVals[z] = pVals[z] + newVals[z];
		}

	}

	public int getDescPriority() {

		if(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descpriority").equals("")){
			if(pNum == 183){
				return 38;
			}else if(pNum == 184){
				return 69;
			}
			return 0;
		}else{		
			return Integer.parseInt(D2TxtFile.ITEM_STAT_COST.getRow(pNum).get("descpriority"));
		}
	}

	public void addCharMods(int[] outStats) {


		switch(pNum){
		
		case(0):
			outStats[0] = outStats[] + pVals[0];
		case():
			outStats[0] = outStats[] + pVals[0];

		
		return outStats;

		}

		System.out.println();


	}


}