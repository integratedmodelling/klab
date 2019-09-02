/*****************************************************************
//
//      	  The CLUE Modelling Framework
//
//                       CLUE-S version
//                   -user interface header file-
//
//
//
//
//
//
//                      Peter Verburg
//     														 				    The CLUE group				 		 *
//
//		        main_source.h
//
//                 all rights: the CLUE group
//                      Wageningen University
//                      peter.verburg@wur.nl
//
********************************************************************/

//---------------------------------------------------------------------------

#ifndef main_sourceH
#define main_sourceH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
#include <jpeg.hpp>
#include <AppEvnts.hpp>
#include <Graphics.hpp>
#include <Buttons.hpp>
#include <FileCtrl.hpp>
#include <Menus.hpp>
#include "PERFGRAP.h"
#include <chartfx3.hpp>
#include <OleCtrls.hpp>

//---------------------------------------------------------------------------
class TForm1 : public TForm
{
__published:	// IDE-managed Components
        TButton *Button1;
        TProgressBar *YearBar;
        TTrackBar *Iterbar;
        TLabel *Iteration;
        TLabel *Label6;
        TLabel *Label7;
        TPanel *Panel1;
        TRichEdit *RichEdit1;
        TButton *SaveButton;
        TButton *Button4;
        TGroupBox *GroupBox1;
        TGroupBox *GroupBox2;
        TBitBtn *BitBtn1;
        TFileListBox *FileListBox1;
        TFileListBox *FileListBox2;
        TLabel *maxdevlabel;
        TButton *CancelButton;
        TMainMenu *MainMenu1;
        TMenuItem *Help1;
        TMenuItem *Help2;
        TMenuItem *About1;
	TStatusBar *bbar;
	TImage *Image1;
	TImage *Image2;
        TMenuItem *FileCheck1;
        TMenuItem *FileCheckon1;
        TMenuItem *FileCheckoff1;
        TMenuItem *In1;
        TMenuItem *Mainparameters1;
        TMenuItem *Regressionresults1;
        TMenuItem *ChangeMatrix1;
        TMenuItem *Neighborhoodsettings1;
        TMenuItem *Neighborhoodresults1;
        TPerformanceGraph *PerformanceGraph1;
        TButton *pausebutton;
        TImage *Image3;
        TMenuItem *Mode1;
        TMenuItem *calcprob1;
        TMenuItem *prepprob1;
        TMenuItem *Metarun1;
        TImage *Image4;
        TLabel *Label1;
        TLabel *Label2;
        TMenuItem *S1;
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall SaveButtonClick(TObject *Sender);
        void __fastcall Button4Click(TObject *Sender);
        void __fastcall BitBtn1Click(TObject *Sender);
        void __fastcall CancelButtonClick(TObject *Sender);
        void __fastcall Help2Click(TObject *Sender);
        void __fastcall About1Click(TObject *Sender);
        void __fastcall testcommandline(TObject *Sender);
        void __fastcall FileCheckon1Click(TObject *Sender);
        void __fastcall FileCheckoff1Click(TObject *Sender);
        void __fastcall Mainparameters1Click(TObject *Sender);
        void __fastcall Regressionresults1Click(TObject *Sender);
        void __fastcall ChangeMatrix1Click(TObject *Sender);
        void __fastcall Neighborhoodsettings1Click(TObject *Sender);
        void __fastcall Neighborhoodresults1Click(TObject *Sender);
        void __fastcall pausebuttonClick(TObject *Sender);
        void __fastcall calcprob1Click(TObject *Sender);
        void __fastcall Metarun1Click(TObject *Sender);
        void __fastcall prepprob1Click(TObject *Sender);
        void __fastcall S1Click(TObject *Sender);
       
private:	// User declarations
        

public:		// User declarations
        __fastcall TForm1(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm1 *Form1;
//---------------------------------------------------------------------------
#endif
