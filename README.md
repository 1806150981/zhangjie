# zhangjie
给我张杰小宝贝创建的仓库
```
000001 IDENTIFICATION    DIVISION.                                              
000002 PROGRAM-ID.       JTDB120B.                                              
000003******************************************************************        
000004*                                                                *        
000005*     ﾆﾎﾝﾃﾚｺﾑｸﾘｴｲﾄ(ｶﾌﾞ)  JTD MT FD 処理                          *    
000006*                                                                *        
000007*     ＪＴＤ  媒体変換（一括同封処理）                           *    
000008*                　  　  CPFSJTDI.                               *        
000009*----------------------------------------------------------------*        
000010*     VERSION     DATE    NAME     ----REMARK----                *        
000015*      1.01     03/12/04  tfp       媒体種別名'CDR'追加          *
000015*      1.00     01/01/19  JTC       ORIGINAL                     *        
000016*                                                                *        
000017******************************************************************        
000018 ENVIRONMENT       DIVISION.                                              
000019 CONFIGURATION     SECTION.                                               
000020 SPECIAL-NAMES.                                                           
000021     C01   IS CH1                                                         
000022     CSP   IS DWT.                                                        
000023 SOURCE-COMPUTER.  M880.                                                  
000024 OBJECT-COMPUTER.  M880.                                                  
000025 INPUT-OUTPUT      SECTION.                                               
000026 FILE-CONTROL.                                                            
000027     SELECT   I-DATA     ASSIGN   SYS300.                                 
000029     SELECT   O-TAKUF    ASSIGN   SYS400.                                 
000031     SELECT   L-DFLIST   ASSIGN   SYS200.                                 
000033     SELECT   L-CKLIST   ASSIGN   SYS290.                                 
000034*                                                                         
000035 DATA              DIVISION.                                              
000036 FILE              SECTION.                                               
000037*                                                                         
000038 FD  I-DATA              LABEL     RECORD    STANDARD                     
000039                         RECORDING MODE      F                            
000040                         BLOCK     CONTAINS  0    RECORDS                 
000041                         RECORD    CONTAINS  500  CHARACTERS.             
000042 01  I-DATA-REC                   PIC  X(500).                            
000049*                                                                         
000050 FD  O-TAKUF             LABEL     RECORD    STANDARD                     
000051                         RECORDING MODE      F                            
000052                         BLOCK     CONTAINS  0    RECORDS                 
000053                         RECORD    CONTAINS  322  CHARACTERS.         QC18
000054 01  O-TAKU-REC                   PIC  X(0322).                       QC18
000053*                        RECORD    CONTAINS  350  CHARACTERS.             
000054*01  O-TAKU-REC                   PIC  X(0350).                           
000055*                                                                         
000061 FD  L-DFLIST            LABEL     RECORD    OMITTED                      
000062                         RECORDING MODE      F                            
000063                         RECORD    CONTAINS  384       CHARACTERS         
000064                         DATA      RECORD    L-DFLIST-DATA.               
000065 01  L-DFLIST-DATA                PIC  X(384).                            
000072*                                                                         
000073 FD  L-CKLIST            LABEL     RECORD    OMITTED                      
000074                         RECORDING MODE      F                            
000075                         RECORD    CONTAINS  384       CHARACTERS         
000076                         DATA      RECORD    L-CKLIST-DATA.               
000077 01  L-CKLIST-DATA                PIC  X(384).                            
000078*                                                                         
000079 WORKING-STORAGE   SECTION.                                               
000080*----------------------------------------------------------------*        
000081*    入力データ                                                  *    
000082*----------------------------------------------------------------*        
000083 COPY  CPFSJTDI.                                                          
000084*----------------------------------------------------------------*        
000085*    氏名編集用PARM                                              *    
000086*----------------------------------------------------------------*        
000087*COPY 'CPP0100B'.                                                         
000087 COPY CPP0100B.                                                         
000089*                                                                         
000101*---------------------------------------------                            
000102 01  WK-KANJI.                                                            
000103     03  KJ-ﾍﾟﾘｶﾝ                 PIC  X(034) VALUE                       
000104         '□ペリカン　　　　　　　　　　'.                            
000104*        'ペリカン                      '.                            QC18
000105*01  KNJ-SP15                     PIC  X(015) VALUE ALL X'A1A1'.          
000105 01  KNJ-SP15                     PIC  X(015) VALUE ALL '　'.             
000106*01  XA1A1                        PIC  X(002) VALUE X'A1A1'.              
000106 01  XA1A1                        PIC  X(002) VALUE '　'.                 
000107*様                                                                     
000108*01  KJ-SAMA                      PIC  X(002) VALUE ALL X'CDCD'.          
000108 01  KJ-SAMA                      PIC  X(002) VALUE ALL '様'.          
000109*                                                                         
000110 01  WK-SEQPARM.                                                          
000111     03  WK-P-SEQ       PIC  9(07).                                       
000112     03  FILLER         PIC  X(73).                                       
000113*---<< ﾌﾗｸﾞ >>---*                                                        
000114 77  WK-SYS300-EOF                PIC  X(003)  VALUE SPACE.               
000115 77  WK-ERR                       PIC  9(002)  VALUE ZERO.                
000116 77  WK-SYS300-ICNT               PIC  9(008)  VALUE ZERO.                
000117 77  WK-SYS300-DCNT               PIC  9(008)  VALUE ZERO.                
000118 77  WK-TAKU-CNT                  PIC  9(008)  VALUE ZERO.                
000120 77  SEQCNT                       PIC  9(007)  VALUE ZERO.                
000121 77  L-DFCNT                      PIC  9(005)  VALUE ZERO.                
000122 77  WK-PAGE-FLG                  PIC  9(001)  VALUE ZERO.                
000123 77  WK-RETURN-CODE               PIC  9(02)   VALUE ZERO.                
000124*---<< ﾌﾞﾚｲｸ ｷ- >>---*                                                  13
000125 01  QWK-BREAK-AREA.                                                    13
000126   03  N-KEY                      PIC  X(010)  VALUE SPACE.               
000132   03  O-KEY                      PIC  X(010)  VALUE SPACE.               
000138*                                                                         
000139*---<< ｿｴｼﾞ ｴﾘｱ >>---*                                                    
000140 01  WK-IDX.                                                              
000141     03  IDX1                     PIC  9(005)  COMP VALUE ZERO.           
000142     03  IDX2                     PIC  9(005)  COMP VALUE ZERO.           
000143     03  IDX3                     PIC  9(005)  COMP VALUE ZERO.           
000144     03  IDX4                     PIC  9(005)  COMP VALUE ZERO.           
000145     03  IDX5                     PIC  9(005)  COMP VALUE ZERO.           
000146     03  IDX6                     PIC  9(005)  COMP VALUE ZERO.           
000147     03  IDX7                     PIC  9(005)  COMP VALUE ZERO.           
000148     03  IDX8                     PIC  9(005)  COMP VALUE ZERO.           
000149     03  IDX9                     PIC  9(005)  COMP VALUE ZERO.           
000170*---<< 宅急便ﾃﾞｰﾀ用 WORK AREA >>---*                              
000171 01  XEROX-WORK.                                                          
000206***同封リスト用                                                         
000207     03  WK-SEIKYU-JOHO.                                                  
000208       05  WK-SEIKYU-JOHO1  OCCURS  200.                                  
000209         07  WK-IVNO              PIC  X(010).                            
000210         07  WK-ISFKB             PIC  X(001).                            
000211         07  WK-ITTCD             PIC  X(007).                            
000212         07  WK-ITTNM             PIC  X(040).                            
000213         07  WK-IBTSB             PIC  X(002).                            
000214         07  WK-IMODE             PIC  X(003).                            
000219     03  WK-ISKYM                 PIC  X(008).                            
000220     03  WK-ISFYB                 PIC  X(008).                            
000221     03  WK-ISFJS1                PIC  X(060).                            
000222     03  WK-ISFJS2                PIC  X(060).                            
000223     03  WK-ISFJS3                PIC  X(060).                            
000224     03  WK-ISFNM1                PIC  X(060).                            
000225     03  WK-ISFNM2                PIC  X(060).                            
000226     03  WK-ISFNM3                PIC  X(060).                            
000227     03  WK-ISFTN                 PIC  X(012).                            
000263*                                                                         
000264*---<< ＪＴＤ宅急便ﾃﾞｰﾀ用 >>---*                                  
000265 01  O-TAKYU-REC.                                                         
000266     03  O-SYYMM                PIC  X(006).                              
000267     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000268     03  O-SGUN                 PIC  X(002).                              
000269     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000270     03  O-VNO                  PIC  X(010).                              
000271     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000272     03  O-TNO                  PIC  X(012).                              
000273     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000274     03  O-SYBN                 PIC  X(008).                              
000275     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000276*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000277     03  O-SJYSHO1              PIC  X(030).                              
000278*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000279     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000280*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000281     03  O-SJYSHO2              PIC  X(030).                              
000282*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000283     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000284*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000285     03  O-SJYSHO3              PIC  X(030).                              
000286*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000287     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000288*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000289     03  O-SNAME1               PIC  X(030).                              
000290*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000291     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000292*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000293     03  O-SNAME2               PIC  X(030).                              
000294*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000295     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000296*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000297     03  O-SNAME3               PIC  X(030).                              
000298*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000299     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000300     03  O-TAKYUKBN             PIC  X(001).                              
000301     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000302*    03  FILLER                 PIC  X(002)  VALUE X'0A42'.               
000303     03  O-TAKYUNAME            PIC  X(030).                              
000304*    03  FILLER                 PIC  X(002)  VALUE X'0A41'.               
000305     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000306     03  O-BITAIKBN             PIC  X(001).                              
000307     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000308     03  O-PRTNO                PIC  X(012).                              
000309     03  FILLER                 PIC  X(001)  VALUE  ','.                  
000310     03  FILLER                 PIC  X(045)  VALUE  SPACE.                
000313*                                                                         
000328*---<< ｿﾉﾀ >>---*                                                         
000329 01  WK-AREA.                                                             
000330     03  PCNT                     PIC  9(006)  VALUE ZERO.                
000331     03  WK-KETA-CNT              PIC  9(003)  VALUE ZERO.                
000332*---<< ｹﾂｶ ﾋﾖｳ >>---*                                                     
000333     03  INCNT                    PIC  9(008)  VALUE ZERO.                
000334     03  OUTCNT                   PIC  9(008)  VALUE ZERO.                
000335*---<< ｼﾖﾘﾋｽﾞｹ,ｼﾖﾘｼﾞｶﾝ ｼﾕﾄｸ >>---*                                        
000336     03  WK-SDATE.                                                        
000337         05  WK-SD-YY             PIC  9(002).                            
000338         05  WK-SD-MM             PIC  9(002).                            
000339         05  WK-SD-DD             PIC  9(002).                            
000340     03  WK-DATE.                                                         
000341         05  WK-YY.                                                       
000342           07  WK-YY1             PIC  X(002).                            
000343           07  WK-YY2             PIC  X(002).                            
000344         05  WK-MM                PIC  X(002).                            
000345         05  WK-DD                PIC  X(002).                            
000346     03  WK-SDATE-X.                                                      
000347         05  WK-SD-YY-X           PIC  X(002).                            
000348         05  WK-SD-MM-X           PIC  X(002).                            
000349         05  WK-SD-DD-X           PIC  X(002).                            
000350     03  WK-EDATE.                                                        
000351         05  WK-ED-YY             PIC  9(002).                            
000352         05  WK-ED-MM             PIC  9(002).                            
000353         05  WK-ED-DD             PIC  9(002).                            
000354     03  WK-STIME                 PIC  X(008).                            
000355     03  WK-STIME-R               REDEFINES WK-STIME.                     
000356         05  WK-ST-HH             PIC  9(002).                            
000357         05  WK-ST-MM             PIC  9(002).                            
000358         05  WK-ST-SS             PIC  9(002).                            
000359         05  FILLER               PIC  X(002).                            
000360     03  WK-ETIME                 PIC  X(008).                            
000361     03  WK-ETIME-R               REDEFINES WK-ETIME.                     
000362         05  WK-ET-HH             PIC  9(002).                            
000363         05  WK-ET-MM             PIC  9(002).                            
000364         05  WK-ET-SS             PIC  9(002).                            
000365         05  FILLER               PIC  X(002).                            
000366     03  WK-HS-YY.                                                        
000367         05  WK-HEISEI-YEAR       PIC  9(002).                            
000368*                                                                         
000369*---<< LBPﾜ-ｸﾖｳ ｴﾘｱ >>---*                                                
000370 01  LBP-WORK-AREA.                                                       
000371     03  WK-STACK.                                                        
000372         05  WK-STACK-FIL01       PIC  X(01)    VALUE '+'.           QC18 
000373         05  WK-STACK-FIL02       PIC  X(08)    VALUE '*RSTACK*'.         
000374     03  WK-DJDEX.                                                        
000375         05  FILLER               PIC  X(01)    VALUE '+'.           QC18 
000376         05  FILLER               PIC  X(15)    VALUE                     
000377             '*DJDE* JDE=FM09'.                                           
000378         05  FILLER               PIC  X(15)    VALUE                     
000379             ',DUPLEX=NO,END;'.                                           
000380*** 同封リスト用                                                          
000381     03  WK-DJDE12.                                                       
000382       05     WK-DJDE0-FIL01      PIC  X(01) VALUE '+'.              QC18 
000383       05     WK-DJDE0-FIL02      PIC  X(25) VALUE                        
000384                                  '*DJDE* FORMAT=FM12,FORMS='.            
000385       05     WK-DJDE0-ｼﾖｼｷID     PIC  X(04) VALUE 'DB02'.                
000386       05     WK-DJDE0-DUPLEX     PIC  X(16) VALUE                        
000387                                  ',DUPLEX=NO,END;'.                      
000507*                                                                         
000508 01  L-SPACE                      PIC  X(256) VALUE SPACE.                
000509*                                                                         
000516*01  X029FF                       PIC  X(003)  VALUE  X'F029FF'.          
000538*****************************                                             
000539*---<< 同封リスト用TBL >>---*                                         
000540*****************************                                             
000541 01  WK-BAITAI-TBL.                                                       
000542     03  WK-BAITAI-ARY.                                                   
000543         05  FILLER               PIC  X(002)  VALUE  '13'.               
000544         05  FILLER               PIC  N(020)  VALUE                      
000545    NC'８インチＦＤ（１ＭＢ）                  '.                     
000555         05  FILLER               PIC  X(002)  VALUE  '15'.               
000565         05  FILLER               PIC  N(020)  VALUE                      
000575    NC'８インチＦＤ（ＩＢＭ）                  '.                     
000576         05  FILLER               PIC  X(002)  VALUE  '23'.               
000577         05  FILLER               PIC  N(020)  VALUE                      
000578    NC'ＣＤＲ（６５０ＭＢ）                    '.                     
000578*** NC'５インチＦＤ（１ＭＢ）                  '.                     
000579         05  FILLER               PIC  X(002)  VALUE  '25'.               
000580         05  FILLER               PIC  N(020)  VALUE                      
000581    NC'５インチＦＤ（ＩＢＭ）                  '.                     
000582         05  FILLER               PIC  X(002)  VALUE  '31'.               
000583         05  FILLER               PIC  N(020)  VALUE                      
000584    NC'３．５インチＦＤ（６４０ＫＢ）          '.                     
000585         05  FILLER               PIC  X(002)  VALUE  '32'.               
000586         05  FILLER               PIC  N(020)  VALUE                      
000587    NC'３．５インチＦＤ（７２０ＫＢ）          '.                     
000588         05  FILLER               PIC  X(002)  VALUE  '33'.               
000589         05  FILLER               PIC  N(020)  VALUE                      
000590    NC'３．５インチＦＤ（１．２ＭＢ）          '.                     
000591         05  FILLER               PIC  X(002)  VALUE  '34'.               
000592         05  FILLER               PIC  N(020)  VALUE                      
000593    NC'３．５インチＦＤ（１．４４ＭＢ）        '.                     
000594         05  FILLER               PIC  X(002)  VALUE  '35'.               
000595         05  FILLER               PIC  N(020)  VALUE                      
000596    NC'３．５インチＦＤ（ＩＢＭ）              '.                     
000597         05  FILLER               PIC  X(002)  VALUE  '41'.               
000598         05  FILLER               PIC  N(020)  VALUE                      
000599    NC'ＭＯ（１２８ＭＢ）                      '.                     
000600         05  FILLER               PIC  X(002)  VALUE  '42'.               
000601         05  FILLER               PIC  N(020)  VALUE                      
000602    NC'ＭＯ（２３０ＭＢ）                      '.                     
000603         05  FILLER               PIC  X(002)  VALUE  '43'.               
000604         05  FILLER               PIC  N(020)  VALUE                      
000605    NC'ＭＯ（６４０ＭＢ）                      '.                     
000606         05  FILLER               PIC  X(002)  VALUE  '51'.               
000607         05  FILLER               PIC  N(020)  VALUE                      
000608    NC'オープンＭＴ（１６００ｂｐｉ）          '.                     
000609         05  FILLER               PIC  X(002)  VALUE  '52'.               
000610         05  FILLER               PIC  N(020)  VALUE                      
000611    NC'オープンＭＴ（６２５０ｂｐｉ）          '.                     
000612         05  FILLER               PIC  X(002)  VALUE  '63'.               
000613         05  FILLER               PIC  N(020)  VALUE                      
000614    NC'カセットＭＴ（３８０００ｂｐｉ）        '.                     
000615         05  FILLER               PIC  X(002)  VALUE  '99'.               
000616         05  FILLER               PIC  N(020)  VALUE                      
000617    NC'その他                                  '.                     
000618     03  WK-BAITAI-ARY-R      REDEFINES WK-BAITAI-ARY.                    
000619         05  FILLER               OCCURS  16.                             
000620               07  TBL-BTSBCD     PIC  X(002).                            
000621               07  TBL-BTSBNM     PIC  N(020).                            
000640*---<< ｴﾝﾄﾞ ﾒﾂｾ-ｼﾞ ｴﾘｱ >>---*                                             
000641 01  WK-END-MSG-TBL.                                                      
000642     03  WK-END-MSG-ARY.                                                  
000643         05  FILLER               PIC  N(020)  VALUE                      
000644             NC'正常終了                                '.             
000645         05  FILLER               PIC  N(020)  VALUE                   
000646             NC'ＪＴＤ一括同封データ無し                '.             
000647         05  FILLER               PIC  N(020)  VALUE                   
000648             NC'                                        '.             
000659     03  WK-END-MSG-ARY-R         REDEFINES WK-END-MSG-ARY.            
000660         05  WK-END-MSG           OCCURS   3                           
000661                                  PIC  N(020).                         
000662*                                                                      
000663* 01  WK-SPACE                   PIC  X(384)  VALUE SPACE.             
000770 01  WK-SPACE.                                                     QC18
           03  WK-SPACE-FIL01         PIC  X(001)  VALUE SPACE.          QC18
           03  WK-SPACE-FIL02         PIC  X(003)  VALUE SPACE.          QC13
           03  WK-SPACE-FIL03         PIC  X(381)  VALUE SPACE.          QC18
000936*    *---<< ﾌﾟﾘﾝﾄ ﾌｵ-ﾏﾂﾄ ｴﾘｱ >>--------------------------------*       
000937*                                                                      
000938 01  L-HEAD1-LINE.                                                     
000939     03  FILLER                   PIC  X(001)  VALUE  ' '.         QC18
000940*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000941*        05  FILLER               PIC  N(019)  VALUE  SPACE.           
000942*        05  FILLER               PIC  N(017)  VALUE                   
000943*            NC'＊  ＊  ＊  一  括  同  封  処  理'.                   
000944*        05  FILLER               PIC  N(020)  VALUE                   
000945*            NC'  結  果  リ  ス  ト    ＊  ＊  ＊      '.             
000946*        05  FILLER               PIC  N(015)  VALUE  SPACE.           
000947*        05  FILLER               PIC  X(005)  VALUE  'PAGE '.         
000948*        05  L-PAGE               PIC  ZZZZ9.                          
000949*                                                                      
000950     03  FILSCODE1            PIC  X(003)  VALUE SPACE.            QC18
000941     03  FILLER               PIC  N(019)  VALUE  SPACE.           QC18
000942     03  FILLER               PIC  N(017)  VALUE                   QC18
000943            NC'＊  ＊  ＊  一  括  同  封  処  理'.                QC18
000944     03  FILLER               PIC  N(020)  VALUE                   QC18
000945            NC'  結  果  リ  ス  ト    ＊  ＊  ＊      '.          QC18
000946     03  FILLER               PIC  N(015)  VALUE  SPACE.           QC18
000947     03  FILLER               PIC  X(005)  VALUE  'PAGE '.         QC18
000948     03  L-PAGE               PIC  ZZZZ9.                          QC18
       01  L-HEAD2-LINE.                                                     
000951     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
000952*     03  FILLER  CHARACTER  TYPE  POINT-9.                            
000950     03  FILSCODE2            PIC  X(003)  VALUE SPACE.            QC18
000953     03  FILLER               PIC  N(010)  VALUE  SPACE.               
000954     03  FILLER               PIC  N(016)  VALUE                       
000955             NC'ＰＧ－ＩＤ  ＝  ＪＴＤＢ１２０Ｂ'.                     
000956*                                                                      
000957 01  L-HEAD3-LINE.                                                     
000958     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
000959*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE3            PIC  X(003)  VALUE SPACE.            QC18
000960     03  FILLER               PIC  N(010)  VALUE  SPACE.               
000961     03  FILLER               PIC  N(017)  VALUE                       
000962             NC'処理区分  ：ＪＴ媒体変換・一括同封'.                   
000963*                                                                      
000964 01  L-HEAD4-LINE.                                                     
000965     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
000966*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE4            PIC  X(003)  VALUE SPACE.            QC18
000967     03  FILLER               PIC  N(010)  VALUE  SPACE.               
000968     03  FILLER               PIC  N(007)  VALUE                       
000969         NC'処理開始  ：  '.                                           
000970     03  L-SDYY               PIC  X(004).                             
000971     03  FILLER               PIC  X(001)  VALUE  '.'.                 
000972     03  L-SDMM               PIC  X(002).                             
000973     03  FILLER               PIC  X(001)  VALUE  '.'.                 
000974     03  L-SDDD               PIC  X(002).                             
000975     03  FILLER               PIC  X(002)  VALUE  '  '.                
000976     03  L-STHH               PIC  X(002).                             
000977     03  FILLER               PIC  X(001)  VALUE  ':'.                 
000978     03  L-STMM               PIC  X(002).                             
000979     03  FILLER               PIC  X(001)  VALUE  ':'.                 
000980     03  L-STSS               PIC  X(002).                             
000981*                                                                      
000982 01  L-HEAD5-LINE.                                                     
000983     03  FILLER               PIC  X(001)  VALUE   ' '.            QC18
000984*     03  FILLER  CHARACTER  TYPE  POINT-9.                            
000950     03  FILSCODE5            PIC  X(003)  VALUE SPACE.            QC18
000985     03  FILLER               PIC  N(010)  VALUE  SPACE.               
000986     03  FILLER               PIC  N(007)  VALUE                       
000987         NC'    終了  ：  '.                                           
000988     03  L-EDYY               PIC  X(004).                             
000989     03  FILLER               PIC  X(001)  VALUE  '.'.                 
000990     03  L-EDMM               PIC  X(002).                             
000991     03  FILLER               PIC  X(001)  VALUE  '.'.                 
000992     03  L-EDDD               PIC  X(002).                             
000993     03  FILLER               PIC  X(002)  VALUE  '  '.                
000994     03  L-ETHH               PIC  X(002).                             
000995     03  FILLER               PIC  X(001)  VALUE  ':'.                 
000996     03  L-ETMM               PIC  X(002).                             
000997     03  FILLER               PIC  X(001)  VALUE  ':'.                 
000998     03  L-ETSS               PIC  X(002).                             
000999*                                                                      
001000 01  L-HEAD6-LINE.                                                     
001001     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
001002*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE6            PIC  X(003)  VALUE SPACE.            QC18
001003     03  FILLER               PIC  N(010)  VALUE  SPACE.               
001004     03  FILLER               PIC  N(007)  VALUE                       
001005             NC'入    力  ：  '.                                       
001006*                                                                      
001007 01  L-HEAD7-LINE.                                                     
001008     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
001009*     03  FILLER  CHARACTER  TYPE  POINT-9.                            
000950     03  FILSCODE7            PIC  X(003)  VALUE SPACE.            QC18
001010     03  FILLER               PIC  N(012)  VALUE  SPACE.               
001011     03  FILLER               PIC  N(011)  VALUE                       
001012         NC'一括同封データ        '.                                   
001013     03  FILLER               PIC  N(002)  VALUE  SPACE.               
001014     03  FILLER               PIC  N(012)  VALUE                       
001015         NC'            件  数＝    '.                                 
001016     03  L-INCNT              PIC  ZZ,ZZZ,ZZ9.                         
001017     03  FILLER               PIC  N(001)  VALUE                       
001018             NC'件'.                                                   
001040*                                                                      
001041 01  L-HEAD8-LINE.                                                     
001042     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
001043*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE8            PIC  X(003)  VALUE SPACE.            QC18
001044     03  FILLER               PIC  N(010)  VALUE  SPACE.               
001045     03  FILLER               PIC  N(007)  VALUE                       
001046          NC'出    力  ：  '.                                          
001047*                                                                      
001048 01  L-HEAD9-LINE.                                                     
001049     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
001050*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE9            PIC  X(003)  VALUE SPACE.            QC18
001051     03  FILLER               PIC  N(012)  VALUE  SPACE.               
001052     03  FILLER               PIC  N(011)  VALUE                       
001053         NC'一括同封対象          '.                                   
001054     03  FILLER               PIC  N(002)  VALUE  SPACE.               
001055     03  FILLER               PIC  N(012)  VALUE                       
001056         NC'            件  数＝    '.                                 
001057     03  L-OUTCNT             PIC  ZZ,ZZZ,ZZ9.                         
001058     03  FILLER               PIC  N(001)  VALUE                       
001059         NC'件'.                                                       
001060*                                                                      
001061 01  L-HEAD10-LINE.                                                    
001062     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
001063*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE10           PIC  X(003)  VALUE SPACE.            QC18
001064     03  FILLER               PIC  N(012)  VALUE  SPACE.               
001065     03  FILLER               PIC  N(011)  VALUE                       
001066         NC'宅急便データファイル  '.                                   
001067     03  FILLER               PIC  N(002)  VALUE  SPACE.               
001068     03  FILLER               PIC  N(012)  VALUE                       
001069         NC'            件  数＝    '.                                 
001070     03  L-TAKUCNT            PIC  ZZ,ZZZ,ZZ9.                         
001071     03  FILLER               PIC  N(001)  VALUE                       
001072         NC'件'.                                                       
001073*                                                                      
001074 01  L-HEAD11-LINE.                                                    
001075     03  FILLER               PIC  X(001)  VALUE  ' '.             QC18
001076*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE11           PIC  X(003)  VALUE SPACE.            QC18
001077     03  FILLER               PIC  N(010)  VALUE  SPACE.               
001078     03  FILLER               PIC  N(009)  VALUE                       
001079         NC'終了コード        '.                                       
001080     03  L-END-CD             PIC  9(002).                             
001081     03  FILLER               PIC  N(001)  VALUE  SPACE.               
001082     03  L-END-JOHO           PIC  N(020).                             
001083*                                                                      
001084*    *---<< 同封物一覧 ﾌﾟﾘﾝﾄｴﾘｱ >>-----------------------------*       
001085*                                                                      
001086 01  L-DFLIST1-LINE.                                                   
001087     03  L-DFLIST1-LINE-FL    PIC  X(001)  VALUE  ' '.             QC18
001088*     03  FILLER  CHARACTER  TYPE  POINT-9.                            
000950     03  FILSCODE12           PIC  X(003)  VALUE SPACE.            QC18
001089     03  FILLER               PIC  N(011)  VALUE  ALL '　'.            
001090     03  FILLER               PIC  N(005)  VALUE                       
001091         NC'＊　＊　＊'.                                               
001100     03  FILLER               PIC  N(016)  VALUE                       
001101         NC'　同　封　物　一　覧　＊　＊　＊'.                         
001111*                                                                      
001112 01  L-DFLIST2-LINE.                                                   
001113     03  L-DFLIST2-LINE-FL    PIC  X(001)  VALUE  ' '.             QC18
001114*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE13           PIC  X(003)  VALUE SPACE.            QC18
001115     03  FILLER               PIC  N(005)  VALUE  ALL '　'.            
001118     03  L-DYY                PIC  ZZZ9.                               
001119     03  FILLER               PIC  N(001)  VALUE  NC'年'.              
001120     03  L-DMM                PIC  Z9.                                 
001121     03  FILLER               PIC  N(001)  VALUE  NC'月'.              
001122     03  L-DGUN               PIC  Z9.                                 
001123     03  FILLER               PIC  N(002)  VALUE                       
001124         NC'群分'.                                                     
001125     03  FILLER               PIC  N(003)  VALUE  ALL '　'.            
001126     03  FILLER               PIC  N(010)  VALUE                       
001127     NC'　同封物コード　：　'.                                         
001129     03  L-DOFUCD             PIC  X(010)  VALUE SPACE.            QC18
001137*                                                                      
001138 01  L-DFLIST3-LINE.                                                   
001139     03  L-DFLIST3-LINE-FL    PIC  X(001)  VALUE  ' '.             QC18
001140*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE14           PIC  X(003)  VALUE SPACE.            QC18
001141     03  FILLER               PIC  N(005)  VALUE  ALL '　'.            
001142     03  FILLER               PIC  N(002)  VALUE                       
001143         NC'〒　'.                                                     
001144     03  L-DYUBIN             PIC  X(008)  VALUE SPACE.            QC18
001157*                                                                      
001158 01  L-DFLIST4-LINE.                                                   
001159     03  L-DFLIST4-LINE-FL        PIC  X(001)  VALUE  '3'.         QC18
001159*    03  FILLER                   PIC  N(006)  VALUE  SPACE.           
           03  FILWIDE01                PIC  X(003)  VALUE  SPACE.       QC18
           03  FILLER                   PIC  N(003)  VALUE  ALL '　'.    QC13
001161*     03  FILLER                   CHARACTER    TYPE IS WIDE.          
001164     03  L-DJUSHO1                PIC  N(030).                         
001165*                                                                      
001166 01  L-DFLIST5-LINE.                                                   
001159     03  L-DFLIST5-LINE-FL        PIC  X(001)  VALUE  '3'.         QC18
001159*    03  FILLER                   PIC  N(006)  VALUE  SPACE.           
           03  FILWIDE02                PIC  X(003)  VALUE  SPACE.       QC18
           03  FILLER                   PIC  N(003)  VALUE  ALL '　'.    QC13
001168*     03  FILLER                   CHARACTER    TYPE IS WIDE.          
001172     03  L-DJUSHO2                PIC  N(030).                         
001173*                                                                      
001174 01  L-DFLIST6-LINE.                                                   
001159     03  L-DFLIST6-LINE-FL        PIC  X(001)  VALUE  '3'.         QC18
001159*    03  FILLER                   PIC  N(006)  VALUE  SPACE.           
           03  FILWIDE03                PIC  X(003)  VALUE  SPACE.       QC18
           03  FILLER                   PIC  N(003)  VALUE  ALL '　'.    QC13
001176*     03  FILLER                   CHARACTER    TYPE IS WIDE.          
001180     03  L-DJUSHO3                PIC  N(030).                         
001181*                                                                      
001182 01  L-DFLIST7-LINE.                                                   
001159     03  L-DFLIST7-LINE-FL        PIC  X(001)  VALUE  '3'.         QC18
001159*    03  FILLER                   PIC  N(006)  VALUE  SPACE.           
           03  FILWIDE04                PIC  X(003)  VALUE  SPACE.       QC18
           03  FILLER                   PIC  N(003)  VALUE  ALL '　'.    QC13
001184*     03  FILLER                   CHARACTER    TYPE IS WIDE.          
001188     03  L-DNAME1                 PIC  N(032).                         
001191*                                                                      
001192 01  L-DFLIST8-LINE.                                                   
001159     03  L-DFLIST8-LINE-FL        PIC  X(001)  VALUE  '3'.         QC18
001159*    03  FILLER                   PIC  N(006)  VALUE  SPACE.           
           03  FILWIDE05                PIC  X(003)  VALUE  SPACE.       QC18
           03  FILLER                   PIC  N(003)  VALUE  ALL '　'.    QC13
001194*     03  FILLER                   CHARACTER    TYPE IS WIDE.          
001196     03  L-DNAME2                 PIC  N(032).                         
001199*                                                                      
001200 01  L-DFLIST9-LINE.                                                   
001159     03  L-DFLIST9-LINE-FL        PIC  X(001)  VALUE  '3'.         QC18
001159*    03  FILLER                   PIC  N(006)  VALUE  SPACE.           
           03  FILWIDE06                PIC  X(003)  VALUE  SPACE.       QC18
           03  FILLER                   PIC  N(003)  VALUE  ALL '　'.    QC13
001202*     03  FILLER                   CHARACTER    TYPE IS WIDE.          
001204     03  L-DNAME3                 PIC  N(032).                         
001207*                                                                      
001208 01  L-DFLIST10-LINE.                                                  
001209     03  L-DFLIST10-LINE-FL   PIC  X(001)  VALUE  ' '.             QC18
001210*    03  FILLER  CHARACTER  TYPE  POINT-9.                             
000950     03  FILSCODE15           PIC  X(003)  VALUE SPACE.            QC18
001211     03  FILLER               PIC  N(007)  VALUE  ALL '　'.            
001212     03  FILLER               PIC  N(007)  VALUE                       
001213         NC'請求先集計番号'.                                           
001214     03  FILLER               PIC  N(007)  VALUE  ALL '　'.            
001215     03  FILLER               PIC  N(007)  VALUE                       
001216         NC'送付先指定区分'.                                           
001217     03  FILLER               PIC  N(003)  VALUE  ALL '　'.            
001218     03  FILLER               PIC  N(006)  VALUE                       
001219         NC'担当者コード'.                                             
001220     03  FILLER               PIC  N(003)  VALUE  ALL '　'.            
001221     03  FILLER               PIC  N(005)  VALUE                       
001222         NC'担当者氏名'.                                               
001223     03  FILLER               PIC  N(018)  VALUE  ALL '　'.            
001224     03  FILLER               PIC  N(005)  VALUE                       
001225         NC'モード名称'.                                               
001226     03  FILLER               PIC  N(008)  VALUE  ALL '　'.            
001227     03  FILLER               PIC  N(004)  VALUE                       
001228         NC'媒体名称'.                                                 
001230*                                                                      
001231 01  L-DFLIST11-LINE.                                                  
001209     03  L-DFLIST11-LINE-FL   PIC  X(001)  VALUE  ' '.             QC18
001233*     03  FILLER  CHARACTER  TYPE  POINT-9.                            
000950     03  FILSCODE16           PIC  X(003)  VALUE SPACE.            QC18
001234     03  FILLER               PIC  N(007)  VALUE  ALL '　'.            
001236     03  L-DVNO               PIC  X(010).                             
001237     03  FILLER               PIC  N(009)  VALUE  ALL '　'.            
001238     03  L-DSFKB              PIC  X(001).                             
001239     03  FILLER               PIC  N(007)  VALUE  ALL '　'.            
001240     03  L-DTTCD              PIC  X(007).                             
001241     03  FILLER               PIC  X(005)  VALUE  SPACE.               
001242     03  L-DTTNM              PIC  N(020).                             
001243     03  FILLER               PIC  N(003)  VALUE  ALL '　'.            
001244     03  FILLER               PIC  X(004)  VALUE  'ﾓｰﾄﾞ'.              
001245     03  L-DMODE              PIC  X(003).                             
001246     03  FILLER               PIC  N(003)  VALUE  ALL '　'.            
001247     03  L-DBAITAI            PIC  N(020).                             
001259*                                                                         
001260 01  L-DFLIST12-LINE.                                                     
001209     03  L-DFLIST12-LINE-FL   PIC  X(001)  VALUE  ' '.             QC18   
001262*    03  FILLER  CHARACTER  TYPE  POINT-9.                                
000950     03  FILSCODE17           PIC  X(003)  VALUE SPACE.            QC18
001263     03  FILLER               PIC  N(065)  VALUE  ALL '　'.               
001264     03  FILLER               PIC  N(006)  VALUE                          
001265         NC'＜　合　計　'.                                                
001266     03  L-DTOTAL             PIC  ZZ,ZZ9.                                
001267     03  FILLER               PIC  N(003)  VALUE                          
001268         NC'件　＞'.                                                      
001304*                                                                         
001305 01  L-DFLIST13-LINE.                                                     
001209     03  L-DFLIST13-LINE-FL   PIC  X(001)  VALUE  ' '.             QC18   
001307*    03  FILLER  CHARACTER  TYPE  POINT-9.                                
000950     03  FILSCODE18           PIC  X(003)  VALUE SPACE.            QC18
001308     03  FILLER               PIC  N(015)  VALUE  ALL '　'.               
001309     03  FILLER               PIC  N(011)  VALUE                          
001310         NC'今回処理は０件でした。'.                                      
002300*                                                                         
002301 01  WK99-ABENDCODE      PIC S9(004) COMP VALUE +4095.                    
002302/                                                                         
002303******************************************************************        
002304*    P R O C E D U R E                                           *        
002305******************************************************************        
002306 PROCEDURE         DIVISION.                                              
002307 CONTROL-SECT      SECTION.                                               
002308 CONTROL-RTN.                                                             
002309     PERFORM  1000-INIT-RTN.                                              
002310*                                                                         
002311     PERFORM  2000-MAIN-RTN                                               
002312              UNTIL (WK-SYS300-EOF = 'END') OR                            
002313                    (WK-ERR  NOT  = 00).                                  
002314     CLOSE    I-DATA.                                                     
002315*                                                                         
002316     PERFORM  3000-END-RTN.                                               
002317     STOP     RUN.                                                        
002318 CONTROL-EXT.                                                             
002319     EXIT.                                                                
002320*                                                                         
002321******************************************************************        
002322*    <1.0>    INITIAL ｼﾖﾘ                                        *        
002323******************************************************************        
002324 1000-INIT-SECT    SECTION.                                               
002325 1000-INIT-RTN.                                                           
002326     OPEN     INPUT     I-DATA                                            
002327              OUTPUT    O-TAKUF  L-DFLIST  L-CKLIST.                      
002334*                                                                         
002339     INITIALIZE         XEROX-WORK.                                       
002342*---<< ﾘｽﾄ ｼﾖﾘ ﾋｽﾞｹ AND ｼﾖﾘ ﾀｲﾑ ｼﾕﾄｸ ,ｾﾂﾃｲ >>---*                         
002343     ACCEPT   WK-SDATE            FROM DATE.                              
002344     ACCEPT   WK-STIME            FROM TIME.                              
002345     IF   WK-SD-YY  =  99                                                 
002346          MOVE  19  TO  WK-YY1                                            
002347     ELSE                                                                 
002348          MOVE  20  TO  WK-YY1                                            
002349     END-IF.                                                              
002350     MOVE     WK-SD-YY            TO   WK-YY2.                            
002351     MOVE     WK-YY               TO   L-SDYY.                            
002352     MOVE     WK-SD-MM            TO   L-SDMM.                            
002353     MOVE     WK-SD-DD            TO   L-SDDD.                            
002354     MOVE     WK-ST-HH            TO   L-STHH.                            
002355     MOVE     WK-ST-MM            TO   L-STMM.                            
002356     MOVE     WK-ST-SS            TO   L-STSS.                            
002357*---<< ｹﾂｶ ﾘｽﾄ ﾆ 00 ｦ ｾﾂﾄ >>---*                                          
002358     MOVE     00                  TO   WK-ERR.                            
002359     WRITE    L-CKLIST-DATA       FROM WK-STACK AFTER DWT.                
002360     WRITE    L-CKLIST-DATA       FROM WK-DJDEX AFTER DWT.                
002361*    --<< ﾊｯｿｳ ﾘｽﾄ ｾｲｷﾞﾖ ﾚｺ-ﾄﾞ ｼﾕﾂﾘﾖｸ >>-----------------------           
002362     WRITE    L-DFLIST-DATA       FROM WK-STACK  AFTER DWT.               
002363     WRITE    L-DFLIST-DATA       FROM WK-DJDE12 AFTER DWT.               
002364*                                                                         
002370 1000-INIT-EXT.                                                           
002371     EXIT.                                                                
002372*                                                                         
002373******************************************************************        
002374*    <2.0>    ﾒｲﾝ ｼﾖﾘ                                            *        
002375******************************************************************        
002376 2000-MAIN-SECT    SECTION.                                               
002377 2000-MAIN-RTN.                                                           
002378     PERFORM  2100-READ-RTN.                                              
002380 2000-MAIN-EXT.                                                           
002381     EXIT.                                                                
002382*                                                                         
002383 2100-READ-RTN    SECTION.                                                
002384*                                                                         
002389     PERFORM  UNTIL (WK-SYS300-EOF = 'END') OR                            
002390                    (WK-ERR  NOT  = 00)                                   
002391       PERFORM  2110-GET-MT-RTN                                           
002396       IF (WK-ERR  =  00) OR (WK-SYS300-ICNT  NOT =  0)                   
002397         IF WK-SYS300-EOF NOT = 'END'                                     
002398           IF   N-KEY  NOT =  O-KEY                                       
002402                PERFORM 2250-DFLST-RTN                                    
002405                PERFORM 2240-TAKUF-RTN                                    
002412                MOVE  ZERO    TO  IDX1                                    
002413                PERFORM 2122-SAVE-RTN                                     
002415                MOVE   N-KEY  TO  O-KEY                                   
002416           ELSE                                                           
002418                PERFORM 2122-SAVE-RTN                                     
002420           END-IF                                                         
002422         END-IF                                                           
002423       END-IF                                                             
002424     END-PERFORM.                                                         
002425 2100-READ-EXT.                                                           
002426     EXIT.                                                                
002427*                                                                         
002428 2110-GET-MT-RTN   SECTION.                                               
002429     READ     I-DATA              INTO CPFSJTDI                           
002430       AT  END                                                            
002431         MOVE 'END'               TO   WK-SYS300-EOF                      
002432         GO   TO        2110-GET-MT-EXT                                   
002433     END-READ.                                                            
002434     ADD      1                   TO   WK-SYS300-ICNT.                    
002438     IF       WK-SYS300-ICNT =  1                                         
002440              MOVE   SJTDI-DFCD   TO   N-KEY  O-KEY                       
002441              MOVE   SJTDI-SKYM   TO   WK-ISKYM                           
002445     ELSE                                                                 
002446              MOVE   SJTDI-DFCD   TO   N-KEY                              
002447     END-IF.                                                              
002449 2110-GET-MT-EXT.                                                         
002450     EXIT.                                                                
002452*                                                                         
002606*----------------------------------------------------------------*        
002608 2122-SAVE-RTN   SECTION.                                                 
002610     ADD     1             TO   IDX1.                                     
002611     MOVE    SJTDI-VNO     TO   WK-IVNO (IDX1).                           
002612     MOVE    SJTDI-SFKB    TO   WK-ISFKB(IDX1).                           
002613     MOVE    SJTDI-TTCD    TO   WK-ITTCD(IDX1).                           
002614     MOVE    SJTDI-TTNM    TO   WK-ITTNM(IDX1).                           
002615     MOVE    SJTDI-MODE    TO   WK-IMODE(IDX1).                           
002616     MOVE    SJTDI-BTSB    TO   WK-IBTSB(IDX1).                           
002626     MOVE    SJTDI-SFYB    TO   WK-ISFYB.                                 
002636     MOVE    SJTDI-SFJS1   TO   WK-ISFJS1.                                
002637     MOVE    SJTDI-SFJS2   TO   WK-ISFJS2.                                
002638     MOVE    SJTDI-SFJS3   TO   WK-ISFJS3.                                
002639     MOVE    SJTDI-SFNM1   TO   WK-ISFNM1.                                
002640     MOVE    SJTDI-SFNM2   TO   WK-ISFNM2.                                
002641     MOVE    SJTDI-SFNM3   TO   WK-ISFNM3.                                
002646     MOVE    SJTDI-SFTN    TO   WK-ISFTN.                                 
002651 2122-SAVE-EXT.                                                           
002652     EXIT.                                                                
002653*                                                                         
003345*----------------------------------------------------------------*        
003346*         宅急便ﾃﾞｰﾀ出力                                            
003347*----------------------------------------------------------------*        
003348 2240-TAKUF-RTN   SECTION.                                                
003378     MOVE   '4'            TO  O-TAKYUKBN.                                
003379     MOVE   KJ-ﾍﾟﾘｶﾝ       TO  O-TAKYUNAME.                               
003382     MOVE   WK-ISKYM(1:6)  TO   O-SYYMM.                                  
003383     MOVE   WK-ISKYM(7:2)  TO   O-SGUN.                                   
003384     MOVE   O-KEY          TO   O-VNO.                                    
003385     MOVE   SPACE          TO   O-TNO.                                    
003386     MOVE   WK-ISFYB       TO   O-SYBN.                                   
003387     MOVE   WK-ISFJS1      TO   O-SJYSHO1.                                
003388     MOVE   WK-ISFJS2      TO   O-SJYSHO2.                                
003389     MOVE   WK-ISFJS3      TO   O-SJYSHO3.                                
003390     MOVE   WK-ISFNM1      TO   O-SNAME1.                                 
003391     MOVE   WK-ISFNM2      TO   O-SNAME2.                                 
003392     MOVE   WK-ISFNM3      TO   O-SNAME3.                                 
003393     MOVE   WK-ISFTN       TO   O-PRTNO.                                  
003396     MOVE   '9'            TO   O-BITAIKBN.                               
003406     ADD    1              TO   WK-TAKU-CNT.                              
003407     WRITE  O-TAKU-REC    FROM  O-TAKYU-REC.                              
003408 2240-TAKUF-EXT.                                                          
003409     EXIT.                                                                
003410*----------------------------------------------------------------*        
003411*         同封リスト出力                                                  
003412*----------------------------------------------------------------*        
003413 2250-DFLST-RTN   SECTION.                                                
003414     ACCEPT   WK-EDATE    FROM   DATE.                                    
003415     ACCEPT   WK-ETIME    FROM   TIME.                                    
003416     ADD    1               TO   OUTCNT.                                  
003419     MOVE   WK-ISKYM(1:4)   TO   L-DYY.                                   
003420     MOVE   WK-ISKYM(5:2)   TO   L-DMM.                                   
003422     MOVE   WK-ISKYM(7:2)   TO   L-DGUN.                                  
003423     MOVE   O-KEY           TO   L-DOFUCD.                                
003424     MOVE   WK-ISFYB        TO   L-DYUBIN.                                
003426     MOVE   WK-ISFJS1       TO   L-DJUSHO1.                               
003427     MOVE   WK-ISFJS2       TO   L-DJUSHO2.                               
003428     MOVE   WK-ISFJS3       TO   L-DJUSHO3.                               
003429     MOVE   WK-ISFNM1       TO   SBNAME1B-IN-NAME1.                       
003430     MOVE   WK-ISFNM2       TO   SBNAME1B-IN-NAME2.                       
003431     MOVE   WK-ISFNM3       TO   SBNAME1B-IN-NAME3.                       
003432     MOVE   KJ-SAMA         TO   SBNAME1B-IN-KEISHO.                      
003433     CALL     'SBNAME1B'    USING SBNAME1B-IN-PARM                        
003434                                  SBNAME1B-OT-PARM.                       
003435     MOVE SBNAME1B-OT-NAME1 TO   L-DNAME1.                                
003436     MOVE SBNAME1B-OT-NAME2 TO   L-DNAME2.                                
003437     MOVE SBNAME1B-OT-NAME3 TO   L-DNAME3.                                
003452*   WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER PAGE.                    
003437     MOVE   '1'             TO    WK-SPACE-FIL01.                    QC18 
003437     MOVE   '   '           TO    WK-SPACE-FIL02.                    QC18 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003453*   WRITE  L-DFLIST-DATA   FROM  L-DFLIST1-LINE  AFTER 2.                 
003437     MOVE   ' '             TO    WK-SPACE-FIL01.                    QC18 
003437     MOVE   SPACE           TO    WK-SPACE-FIL02.                    QC18 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003453     WRITE  L-DFLIST-DATA   FROM  L-DFLIST1-LINE  AFTER 1.           QC18 
003454*   WRITE  L-DFLIST-DATA   FROM  L-DFLIST2-LINE  AFTER 2.                 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003454     WRITE  L-DFLIST-DATA   FROM  L-DFLIST2-LINE  AFTER 1.           QC18 
003455*   WRITE  L-DFLIST-DATA   FROM  L-DFLIST3-LINE  AFTER 4.                 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003455     WRITE  L-DFLIST-DATA   FROM  L-DFLIST3-LINE  AFTER 1.           QC18 
      *                                                                    QC18 
003456     WRITE  L-DFLIST-DATA   FROM  L-DFLIST4-LINE  AFTER 1.                
003457     WRITE  L-DFLIST-DATA   FROM  L-DFLIST5-LINE  AFTER 1.                
003458     WRITE  L-DFLIST-DATA   FROM  L-DFLIST6-LINE  AFTER 1.                
      *                                                                    QC18 
003459*   WRITE  L-DFLIST-DATA   FROM  L-DFLIST7-LINE  AFTER 2.                 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003459     WRITE  L-DFLIST-DATA   FROM  L-DFLIST7-LINE  AFTER 1.           QC18 
      *                                                                    QC18 
003460     WRITE  L-DFLIST-DATA   FROM  L-DFLIST8-LINE  AFTER 1.                
003461     WRITE  L-DFLIST-DATA   FROM  L-DFLIST9-LINE  AFTER 1.                
      *                                                                    QC18 
003462*   WRITE  L-DFLIST-DATA   FROM  L-DFLIST10-LINE AFTER 4.                 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003452     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                 QC18 
003462     WRITE  L-DFLIST-DATA   FROM  L-DFLIST10-LINE AFTER 1.           QC18 
      *                                                                    QC18 
003463     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                      
003468     MOVE   1        TO  L-DFCNT.                                         
003469     PERFORM   VARYING  IDX2  FROM  1  BY  1                              
003470                 UNTIL  IDX2  >  IDX1                                     
003471             MOVE   WK-IVNO (IDX2)  TO  L-DVNO                            
003472             MOVE   WK-ISFKB(IDX2)  TO  L-DSFKB                           
003473             MOVE   WK-ITTCD(IDX2)  TO  L-DTTCD                           
003474             MOVE   WK-ITTNM(IDX2)  TO  L-DTTNM                           
003475             MOVE   WK-IMODE(IDX2)  TO  L-DMODE                           
003476             PERFORM   VARYING  IDX3  FROM  1  BY  1                      
003477                         UNTIL  IDX3  >  16                               
003478                      IF  WK-IBTSB(IDX2)  =  TBL-BTSBCD(IDX3)             
003479                          MOVE  TBL-BTSBNM(IDX3)  TO  L-DBAITAI           
003480                      END-IF                                              
003481             END-PERFORM                                                  
003482             IF     L-DFCNT  >  40                                        
003489                    MOVE   WK-ISKYM(1:4)   TO   L-DYY                     
003490                    MOVE   WK-ISKYM(5:2)   TO   L-DMM                     
003491                    MOVE   WK-ISKYM(7:2)   TO   L-DGUN                    
003492                    MOVE   O-KEY           TO   L-DOFUCD                  
003493*                 WRITE  L-DFLIST-DATA   FROM  WK-SPACE                   
003494*                                                   AFTER PAGE            
003437                    MOVE   '1'             TO   WK-SPACE-FIL01      QC18  
003437                    MOVE   '   '           TO   WK-SPACE-FIL02      QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003495*                 WRITE  L-DFLIST-DATA FROM L-DFLIST1-LINE                
003496*                                                   AFTER 2               
003437                    MOVE   ' '             TO L-DFLIST1-LINE-FL     QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003495                    WRITE  L-DFLIST-DATA FROM L-DFLIST1-LINE        QC18  
003494                                                    AFTER 1         QC18  
003497*                 WRITE  L-DFLIST-DATA FROM L-DFLIST2-LINE                
003498*                                                   AFTER 2               
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003497                    WRITE  L-DFLIST-DATA FROM L-DFLIST2-LINE        QC18  
003494                                                    AFTER 1         QC18  
003499*                 WRITE  L-DFLIST-DATA FROM L-DFLIST10-LINE               
003500*                                                   AFTER 15              
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003499                   WRITE  L-DFLIST-DATA FROM L-DFLIST10-LINE        QC18  
003494                                                    AFTER 1         QC18  
003501*                 WRITE  L-DFLIST-DATA FROM L-DFLIST11-LINE               
003502*                                                   AFTER 2               
003493                    WRITE  L-DFLIST-DATA   FROM  WK-SPACE           QC18  
003494                                                    AFTER 1         QC18  
003501                    WRITE  L-DFLIST-DATA FROM L-DFLIST11-LINE       QC18  
003502                                                    AFTER 2         QC18  
003503                    MOVE   1    TO  L-DFCNT                               
003504             ELSE                                                         
003437                    MOVE   ' '             TO L-DFLIST11-LINE-FL    QC18  
003505                    WRITE  L-DFLIST-DATA FROM L-DFLIST11-LINE             
003506                                                      AFTER 1             
003507                    ADD    1    TO  L-DFCNT                               
003508             END-IF                                                       
003509     END-PERFORM.                                                         
003510     MOVE   IDX1      TO     L-DTOTAL.                                    
003511*   WRITE  L-DFLIST-DATA   FROM  L-DFLIST12-LINE  AFTER 2.                
003437     MOVE   ' '        TO       WK-SPACE-FIL01.                     QC18  
003437     MOVE   SPACE      TO       WK-SPACE-FIL02.                     QC18  
004135     WRITE  L-DFLIST-DATA   FROM  WK-SPACE  AFTER 1.                QC18  
003511     WRITE  L-DFLIST-DATA   FROM  L-DFLIST12-LINE  AFTER 1.         QC18  
003512*                                                                         
003513 2250-DFLST-EXT.                                                          
003514     EXIT.                                                                
004110*----------------------------------------------------------------*        
004111*   <3.0>  ｴﾝﾄﾞ ｼﾖﾘ                                              *        
004112*----------------------------------------------------------------*        
004113 3000-END-SECT     SECTION.                                               
004114 3000-END-RTN.                                                            
004115     IF   WK-SYS300-ICNT  NOT =  0                                        
004120          PERFORM  2240-TAKUF-RTN                                         
004125          PERFORM  2250-DFLST-RTN                                         
004128     ELSE                                                                 
004131          MOVE  ZERO        TO    L-DGUN                                  
004132          MOVE  WK-YY       TO    L-DYY                                   
004133          MOVE  WK-SD-MM    TO    L-DMM                                   
004134*        WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER PAGE                  
003437          MOVE   '1'        TO       WK-SPACE-FIL01                 QC18  
003437          MOVE   '   '      TO       WK-SPACE-FIL02                 QC18  
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004135*        WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 2                     
003437          MOVE   ' '        TO       WK-SPACE-FIL01                 QC18  
003437          MOVE   SPACE      TO       WK-SPACE-FIL02                 QC18 
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
      *                                                                   QC18  
004136          WRITE L-DFLIST-DATA  FROM  L-DFLIST1-LINE  AFTER 1              
      *                                                                   QC18  
004137*        WRITE L-DFLIST-DATA  FROM  L-DFLIST2-LINE  AFTER 2               
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004137          WRITE L-DFLIST-DATA  FROM  L-DFLIST2-LINE  AFTER 1        QC18  
004138*        WRITE L-DFLIST-DATA  FROM  L-DFLIST3-LINE  AFTER 2               
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004138          WRITE L-DFLIST-DATA  FROM  L-DFLIST3-LINE  AFTER 1        QC18  
004139*        WRITE L-DFLIST-DATA  FROM  L-DFLIST13-LINE AFTER 4               
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004135          WRITE L-DFLIST-DATA  FROM  WK-SPACE  AFTER 1              QC18  
004139          WRITE L-DFLIST-DATA  FROM  L-DFLIST13-LINE AFTER 1        QC18  
004140     END-IF.                                                              
004142     PERFORM  KEKKA-LIST-RTN.                                             
004143     CLOSE    O-TAKUF  L-DFLIST  L-CKLIST.                                
004144*                                                                         
004145     IF       WK-ERR = ZERO                                               
004146              MOVE ZERO           TO   RETURN-CODE                        
004147     ELSE                                                                 
004148              MOVE 4095           TO   RETURN-CODE                        
004149              CALL 'CBLABN'   USING     WK99-ABENDCODE                    
004150     END-IF.                                                              
004151 3000-END-EXT.                                                            
004152     EXIT.                                                                
004153*----------------------------------------------------------------*        
004154*   処理結果リスト作成                                           *    
004155*----------------------------------------------------------------*        
004156 KEKKA-LIST-RTN  SECTION.                                                 
004157     ACCEPT   WK-EDATE            FROM DATE.                              
004158     ACCEPT   WK-ETIME            FROM TIME.                              
004159     IF   WK-ED-YY  =  99                                                 
004160          MOVE  19  TO  WK-YY1                                            
004161     ELSE                                                                 
004162          MOVE  20  TO  WK-YY1                                            
004163     END-IF.                                                              
004164     MOVE     WK-ED-YY            TO   WK-YY2.                            
004165     MOVE     WK-YY               TO   L-EDYY.                            
004166     MOVE     WK-ED-MM            TO   L-EDMM.                            
004167     MOVE     WK-ED-DD            TO   L-EDDD.                            
004168     MOVE     WK-ET-HH            TO   L-ETHH.                            
004169     MOVE     WK-ET-MM            TO   L-ETMM.                            
004170     MOVE     WK-ET-SS            TO   L-ETSS.                            
004171     MOVE     1                   TO   L-PAGE.                            
004172     MOVE     WK-SYS300-ICNT      TO   L-INCNT.                           
004175     MOVE     OUTCNT              TO   L-OUTCNT.                          
004176     MOVE     WK-TAKU-CNT         TO   L-TAKUCNT.                         
004177     MOVE     WK-ERR              TO   L-END-CD.                          
004178     MOVE     WK-END-MSG (WK-ERR + 1)                                     
004179                                  TO   L-END-JOHO.                        
004180*   WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER PAGE.                
003437     MOVE     '1'            TO   WK-SPACE-FIL01                    QC18  
003437     MOVE     '   '          TO   WK-SPACE-FIL02                    QC18  
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004181*   WRITE    L-CKLIST-DATA  FROM L-HEAD1-LINE  AFTER 2.                   
003437     MOVE     ' '            TO   WK-SPACE-FIL01                    QC18  
003437     MOVE     SPACE          TO   WK-SPACE-FIL02                    QC18  
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004181     WRITE    L-CKLIST-DATA  FROM L-HEAD1-LINE  AFTER 1.            QC18  
004182*   WRITE    L-CKLIST-DATA  FROM L-HEAD2-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004182     WRITE    L-CKLIST-DATA  FROM L-HEAD2-LINE  AFTER 1.            QC18  
004183*   WRITE    L-CKLIST-DATA  FROM L-HEAD3-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004183     WRITE    L-CKLIST-DATA  FROM L-HEAD3-LINE  AFTER 1.            QC18  
004184*   WRITE    L-CKLIST-DATA  FROM L-HEAD4-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004184     WRITE    L-CKLIST-DATA  FROM L-HEAD4-LINE  AFTER 1.            QC18  
004185*   WRITE    L-CKLIST-DATA  FROM L-HEAD5-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004185     WRITE    L-CKLIST-DATA  FROM L-HEAD5-LINE  AFTER 1.            QC18  
004186*   WRITE    L-CKLIST-DATA  FROM L-HEAD6-LINE  AFTER 3.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004186     WRITE    L-CKLIST-DATA  FROM L-HEAD6-LINE  AFTER 1.            QC18  
004187*   WRITE    L-CKLIST-DATA  FROM L-HEAD7-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004187     WRITE    L-CKLIST-DATA  FROM L-HEAD7-LINE  AFTER 1.            QC18  
004188*   WRITE    L-CKLIST-DATA  FROM L-HEAD8-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004188     WRITE    L-CKLIST-DATA  FROM L-HEAD8-LINE  AFTER 1.            QC18  
004189*   WRITE    L-CKLIST-DATA  FROM L-HEAD9-LINE  AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004189     WRITE    L-CKLIST-DATA  FROM L-HEAD9-LINE  AFTER 1.            QC18  
004190*   WRITE    L-CKLIST-DATA  FROM L-HEAD10-LINE AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004190     WRITE    L-CKLIST-DATA  FROM L-HEAD10-LINE AFTER 1.            QC18  
004191*   WRITE    L-CKLIST-DATA  FROM L-HEAD11-LINE AFTER 2.                   
004180     WRITE    L-CKLIST-DATA  FROM WK-SPACE      AFTER 1.            QC18  
004191     WRITE    L-CKLIST-DATA  FROM L-HEAD11-LINE AFTER 1.            QC18  
004193 KEKKA-LIST-EXT.                                                          
004194     EXIT.                                                                
004195*                                                                         
```
