

public class BILTEXT501002 extends ChMBNomalBase{

	/** 再計算対象（中間）ファイル */
	private BillBaTargetCalcBsCsvDao billBaTargetCalcBsCsvDao = null;

	/** 再計算対象外（中間）ファイル */
	private BillBatTargetCalcOutCsvDao billBatTargetCalcOutCsvDao = null;

	/** 請求練習用対象ファイル  */
	private BillLearinigDiscountCsvDao billLearinigDiscountCsvDao = null;

	/** OKファイル */
	private BillLearinigDiscountCsvDao okDao = null;

	/** NGファイル */
	private BillLearinigDiscountCsvDao ngDao = null;
	/**
	 * 
	 * Chmb初期処理。
	 * 
	 * @param args 起動引数
	 */
	@Override
	public void doInit(List<String> args) {
		// JOBID
		super.setJobId(args.get(NumConstant.CHMB_ARGS_JOB_ID));
		// 再計算対象（中間）ファイルパス
		String inputPath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_1);
		// 再計算対象外（中間）ファイルパス
		String outoftargetOutputPath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_2);
		// 請求練習用対象ファイルパス
		String targetFilePath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_3);
		// OKファイルパス
		String okFilePath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_4);
		// NGファイルパス
		String ngFilePath = args.get(NumConstant.CHMB_ARGS_OTHER_DATA_5);

		// 再計算対象（中間）ファイル
		billBaTargetCalcBsCsvDao = new BillBaTargetCalcBsCsvDao();
		billBaTargetCalcBsCsvDao.setFileName(inputPath, CodeConstant.StringConstant.CHARSET_NAME, false);

		// 再計算対象外（中間）ファイル
		billBatTargetCalcOutCsvDao = new BillBatTargetCalcOutCsvDao();
		billBatTargetCalcOutCsvDao.setFileName(outoftargetOutputPath, CodeConstant.StringConstant.CHARSET_NAME, true);

		// 請求練習用対象ファイル
		billLearinigDiscountCsvDao = new BillLearinigDiscountCsvDao();
		billLearinigDiscountCsvDao.setFileName(targetFilePath, CodeConstant.StringConstant.CHARSET_NAME, true);

		// OKファイル
		okDao = new BillLearinigDiscountCsvDao();
		okDao.setFileName(okFilePath, CodeConstant.StringConstant.CHARSET_NAME, true);

		// NGファイル
		ngDao = new BillLearinigDiscountCsvDao();
		ngDao.setFileName(ngFilePath, CodeConstant.StringConstant.CHARSET_NAME, true);
	}

	/**
	 * 起動パラメータチェック。
	 * 
	 * @param args 起動引数
	 */
	@Override
	public void checkParameters(List<String> args) {
		
		/** 起動引数の個数。 */
		int initParamNum = 6;

		if (args.size() != initParamNum) {
			// 起動引数の個数チェック
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509123);
		}
		
	}

	/**
	 * アダプタ主処理。
	 * 
	 * @param args 起動引数
	 */
	@Override
	public void doMain(List<String> args) {
		try {
			BillLearinigDiscountCsv billLearinigDiscountCsv = billLearinigDiscountCsvDao.readLine();
			
			List<BillLearinigDiscountCsv> billLearinigDiscountCsvList = new ArrayList();
			while((billLearinigDiscountCsv = billLearinigDiscountCsvDao.readLine())!=null){
				if(billLearinigDiscountCsv!=null){
					billLearinigDiscountCsvList.add(billLearinigDiscountCsv);
				}
			}
				// msgを作成する
				BillLearinigDiscountMsg msg = new BillLearinigDiscountMsg();
				
				List<BillLearinigDiscountCsv> billLearinigDiscountCsvNew = new ArrayList();
				//我的想法是：如果返回true我就更新，但是我的有东西先把我合计后的接受一下，然后再更新进去，对吧
	   			String keyBillGroupIdAndServiceId = null;
			for(BillLearinigDiscountCsv item:billLearinigDiscountCsvList){
				if(Renewmessage(item,billLearinigDiscountCsvNew)){
					keyBillGroupIdAndServiceId = 
				}
				}else{
				}
	
	}

			
			
		} catch (CsvFileAccessException e) {
			throw new ChMBSystemException(ErrBILCdeConstant.ERROR_MESSAGE_BIL_E509125, e, jobId);
		}
		
	}


		
		return outCsv;
	}
	public boolean Renewmessage(BillLearinigDiscountCsv item,List<BillLearinigDiscountCsv> billLearinigDiscountCsvNew) {
	
		for(BillLearinigDiscountCsv billLearinigDiscountCsv : billLearinigDiscountCsvNew) {
			if(billLearinigDiscountCsv.getBillGroupId().equals(item.getBillGroupId())&&billLearinigDiscountCsv.getServiceId().equals(item.getServiceId())) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	

	}
}
	
	
	
