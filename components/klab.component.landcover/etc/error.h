//---------------------------------------------------------------------------

#ifndef errorH
#define errorH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "finish.h"
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class Terrorbox : public Tfinishbox
{
__published:	// IDE-managed Components
        TLabel *Label2;

private:	// User declarations
public:		// User declarations
        __fastcall Terrorbox(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE Terrorbox *errorbox;
//---------------------------------------------------------------------------
#endif
